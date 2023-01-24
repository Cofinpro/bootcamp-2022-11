import {defineStore} from "pinia";
import axiosInstance from "@/axios";

export const useComicStore = defineStore('ComicStore', {
    state: () => ({
        source: String(""),
        description: String(""),
        title: String(""),
    }),
    actions: {
        async loadComicOfTheDay(): Promise<void> {
            await axiosInstance.get("/comic/info.0.json")
                .then(res => res.data)
                .then(data => {
                    this.source = data.img;
                    this.description = data.alt;
                    this.title = data.safe_title;
                });
        }
    }
})