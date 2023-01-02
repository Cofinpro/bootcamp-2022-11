<template>
  <div class="image-upload" v-on:click="openFileDialog">
    <input type="file" ref="imageInput" class="hidden"/>
    <img v-if="imageDataUri" alt="Profilbild" v-bind:src="imageDataUri" class="image"/>
    <img v-else alt="Profilbild" src="@/assets/images/dummy_profilePicture.png" class="image"/>
    <div class="upload-text">Profilbild ändern</div>
  </div>
</template>

<script>
export default {
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
