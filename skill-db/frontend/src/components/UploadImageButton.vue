<template>
  <div class="image-upload" v-on:click="openFileDialog">
    <input type="file" ref="imageInput" class="hidden"/>
    <img v-if="imageDataUri" alt="Profilbild" v-bind:src="imageDataUri" class="image"/>
    <img v-else alt="Profilbild" src="@/assets/images/dummy_profilePicture.png" class="image"/>
    <div class="upload-text">Profilbild ändern</div>
  </div>
  <v-btn class="mt-3 mb-10 d-flex justify-center elevation-0" size="small" @click="openFileDialog">Bild hochladen</v-btn>
</template>

<script>
export default {
  data() {
    return {
      maxUploadSize: 20000000, // Angabe in bytes, entspricht 20 MB
      imageDataUri: null,
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
    async uploadImage() {
      const fileInput = this.$refs.imageInput;

      if (fileInput && fileInput.value) {
        const file = fileInput.files[0];

        if (file instanceof Blob && this.isPermissibleSize(file)) {
          const image = new Image();
          image.onload = () => {
            if (this.isInPortraitMode(image)) {
              const reader = new FileReader();
              reader.onload = event => {
                this.imageDataUri = event.target.result;
              }
              reader.readAsDataURL(file);
            } else {
              console.error('Das Bild muss im Hochformat vorliegen!')
            }
          };
          image.src = URL.createObjectURL(file);
        } else {
          console.error('Die Datei muss kleiner als 20 MB sein!');
        }
      }
    }
  },

  mounted() {
    if (this.$refs.imageInput) {
      this.$refs.imageInput.addEventListener('change', this.uploadImage)
    }
  }
}
</script>

<style>
.image-upload {
  position: relative;
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
  color: #999;
}


.image {
  width: 200px;
  height: 200px;
  object-fit: cover;
}

.hidden {
  display: none;
}
</style>
