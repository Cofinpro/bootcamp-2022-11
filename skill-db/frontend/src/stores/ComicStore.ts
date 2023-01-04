import {defineStore} from "pinia";
import axios from "axios";

export const useComicStore = defineStore('ComicStore', {
    state: () => ({
        source: String(""),
        description: String(""),
        title: String(""),
    }),
    actions: {
        loadComicOfTheDay(): void {
            axios.get("/comic/info.0.json")
                .then(res => res.data)
                .then(data => {
                    this.source = data.img;
                    this.description = data.alt;
                    this.title = data.safe_title;
                });
        }
    }
})