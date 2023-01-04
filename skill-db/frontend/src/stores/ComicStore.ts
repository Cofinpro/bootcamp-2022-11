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
            //axios.defaults.headers.get['Access-Control-Allow-Origin'] = '*';
            axios.get("https://xkcd.com/info.0.json")
                .then(res => res.data)
                .then(data => {
                    console.log("imgsource: ", data.img);
                    this.source = data.img;
                    this.description = data.alt;
                    this.title = data.safe_title;
                });
        }
    }
})