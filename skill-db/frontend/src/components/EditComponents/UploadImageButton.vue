<template>
  <div class="image-upload">
    <input
        type="file"
        ref="imageInput"
        class="hidden"
        accept="image/png, image/jpg, image/jpeg"
        @input="convertImageAndEmitToParent"/>

    <img class="image"
         v-if="imageDataUri"
         alt="Profilbild"
         :src="imageDataUri"/>
    <img class="image"
         v-else-if="oldPic"
         alt="Profilbild"
         :src="oldPic"/>
    <img class="image"
         v-else
         alt="Profilbild"
         src="@/assets/images/dummy_profilePicture.png"/>

    <v-btn v-if="(oldPic || imageDataUri)"
           class="d-flex justify-center button mb-2"
           elevation="0"
           width="200"
           size="small"
           @click.prevent="deleteProfilePicture">
      Bild Löschen
    </v-btn>
    <div class="upload-text">
      <v-btn elevation="0"
             size="small"
             @click="openFileDialog">
        Profilbild ändern
      </v-btn>
    </div>
  </div>
</template>

<script>
import {useErrorStore} from "@/stores/ErrorStore";
import {ref, onMounted} from "vue";

export default {
  name: "UploadImageButton",
  emits: ['toggleDelete', 'upload'],
  props: {
    oldPicture: String,
  },
  setup(props, context) {
    const errorStore = useErrorStore();
    const MAX_UPLOAD_SIZE = 20000000;  // Angabe in bytes, entspricht 20 MB
    const imageDataUri = ref('');
    const oldPic = ref(props.oldPicture);
    const imageInput = ref();

    onMounted(() => {
      console.log(imageInput.value)
    });
    function deleteProfilePicture() {
      if (oldPic.value && !imageDataUri) {
        //image is persisted
        imageDataUri.value = '';
        imageInput.value = undefined;
        convertImageAndEmitToParent();
        context.emit('toggleDelete', true);
      }
      //image is not persisted yet, local deleting sufficient
      else {
        oldPic.value = '';
        imageDataUri.value = '';
        imageInput.value = undefined;
        context.emit('toggleDelete', true);
        convertImageAndEmitToParent();
      }
    }
    async function convertImageAndEmitToParent() {
      if (imageInput && imageInput.value) {
        const file = imageInput.value.files[0];
        if (!(file instanceof Blob) || ! isPermissibleSize(file)) {
          this.errorStore.catchUploadImageError(new Error('Falsche Größe: max. 20 MB zulässig!'));
          return;
        }
        const image = new Image();
        image.onload = () => {
          if (!isInPortraitMode(image)) {
            errorStore.catchUploadImageError(new Error('Falsches Format: Nur Hochformat zulässig!'));
            return;
          }
          const reader = new FileReader();
          reader.onload = event => {
            imageDataUri.value = event.target.result;
            context.emit('upload', imageDataUri.value);
            context.emit('toggleDelete', false)
          }
          reader.readAsDataURL(file);
        };
        image.src = URL.createObjectURL(file);
      } else {
        context.emit('upload', undefined)
      }
    }
    async function openFileDialog() {
      imageInput.value.click()
    }
    function isPermissibleSize(file) {
      return file.size <= MAX_UPLOAD_SIZE;
    }

    function isInPortraitMode(image) {
      return image.height > image.width;
    }
    return {
      errorStore,
      MAX_UPLOAD_SIZE,
      imageDataUri,
      oldPic,
      imageInput,
      deleteProfilePicture,
      convertImageAndEmitToParent,
      openFileDialog
    }
  },
}
</script>

<style scoped>

.image-upload {
  position: relative;
  border: 1px solid dimgray;
  width: 200px;
  height: 200px;
}

.image-upload:hover .upload-text {
  display: block;
}


.upload-text {
  display: none;
  position: absolute;
  text-align: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.hidden {
  display: none;
}

.button {
  margin-bottom: 20px;
}

</style>
