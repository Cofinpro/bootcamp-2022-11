import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://skilldbbackend-env.eba-aqy7m5zk.eu-central-1.elasticbeanstalk.com/',
        changeOrigin: true,
      },
      '/comic': {
        target: 'https://xkcd.com/',
        changeOrigin: true,
        headers: {
          'Access-Control-Allow-Origin': '*'
        },
        rewrite: (path) => path.replace(/^\/comic/, '')
      },
    }
  }
})
