import {fileURLToPath, URL} from 'node:url'
/// <reference types="vitest" />
import {defineConfig} from 'vite'
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
                target: 'http://localhost:8080/',
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
    },
    test: {
        setupFiles: "vuetify.config.js",
        deps: {
            inline: ["vuetify"],
        },
        globals: true,
    },
})
