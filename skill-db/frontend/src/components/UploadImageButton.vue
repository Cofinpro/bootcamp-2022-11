<template>
  <div class="image-upload">
    <input type="file" ref="imageInput" class="hidden"/>
    <img class="image" v-if="imageDataUri" alt="Profilbild" v-bind:src="imageDataUri"/>
    <img class="image" v-else alt="Profilbild" src="@/assets/images/dummy_profilePicture.png"/>
    <div class="upload-text">
      <v-btn elevation="0" size="small"
             @click="openFileDialog">
        Profilbild ändern
      </v-btn>
    </div>
  </div>
</template>

<script> /*TODO should be TypeScript*/
export default {
  name:"UploadImageButton",
  data() {
    return {
      imageDataUri: null,
    }
  },
  methods: {
    async openFileDialog() {
      this.$refs.imageInput.click();
    },
    async uploadImage() {
      const fileInput = this.$refs.imageInput

      if (fileInput && fileInput.value) {
        const file = fileInput.files[0]

        if (file instanceof Blob) {
          const reader = new FileReader()
          reader.onload = event => {
            this.imageDataUri = event.target.result
          }
          reader.readAsDataURL(file)
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

<style scoped>

.image-upload {
  position: relative;
  width: 200px;
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
  width: 200px;
  height: 200px;
  object-fit: cover;
}

.hidden {
  display: none;
}
</style>
