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

export default {
  name: "UploadImageButton",
  props: {
    oldPicture: String,
  },
  data(props) {
    return {
      errorStore: useErrorStore(),
      maxUploadSize: 20000000, // Angabe in bytes, entspricht 20 MB
      imageDataUri: null,
      oldPic: props.oldPicture
    }
  },
  methods: {
    isPermissibleSize(file) {
      return file.size <= this.maxUploadSize;
    },
    isInPortraitMode(image) {
      return image.height > image.width;
    },
    async openFileDialog() {
      this.$refs.imageInput.click();
    },
    deleteProfilePicture() {
      if (this.oldPicture && !this.imageDataUri) {
        //image is persisted
        this.imageDataUri ='';
        this.$refs.imageInput.value='';
        this.convertImageAndEmitToParent();
        this.$emit('toggleDelete', true);
      }
      //image is not persisted yet, local deleting sufficient
      this.oldPic='';
      this.imageDataUri ='';
      this.$refs.imageInput.value='';
      this.$emit('toggleDelete', true);
      this.convertImageAndEmitToParent();
    },
    async convertImageAndEmitToParent() {
      console.log(this.oldPic)
      const fileInput = this.$refs.imageInput;
      if (fileInput && fileInput.value) {
        const file = fileInput.files[0];
        if (!(file instanceof Blob) || !this.isPermissibleSize(file)) {
          this.errorStore.catchUploadImageError(new Error('Falsche Größe: max. 20 MB zulässig!'));
          return;
        }
        const image = new Image();
        image.onload = () => {
          if (!this.isInPortraitMode(image)) {
            this.errorStore.catchUploadImageError(new Error('Falsches Format: Nur Hochformat zulässig!'));
            return;
          }
          const reader = new FileReader();
          reader.onload = event => {
            this.imageDataUri = event.target.result;
            this.$emit('upload', this.imageDataUri);
            this.$emit('toggleDelete', false)
          }
          reader.readAsDataURL(file);
        };

        image.src = URL.createObjectURL(file);
      }
      else{
        this.$emit('upload',undefined)
      }
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
  /*color: #9bc3ee;
  background-color: #ffffff;
  border-radius: 5px;*/
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
