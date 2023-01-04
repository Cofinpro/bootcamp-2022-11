<template>
  <v-img
      cover
      max-height="100vh"
      src="https://imgs.xkcd.com/comics/pando.png"
  ></v-img>
</template>

<script>
import axios from "axios";

export default {
  name: "ComicOfTheDay",
   setup() {
    let imgSource = "";

    axios.get("https://xkcd.com/info.0.json").then(res => console.log(res.data));

    /*

    axios.get("https://xkcd.com/rss.xml").then(response => response.data)
        .then(str => new DOMParser().parseFromString(str, "text/xml"))
        .then(data => {
          console.log("rss feed data: ", data);
          const items = data.getElementsByTagName("item");
          console.log("items", items);
          const description = items.item(0).querySelector("description");
          imgSource = description.toString().startsWith("\"");
          console.log("imgSource", imgSource);
        });

     */

    return {
      imgSource
    }
  }
}
</script>

<style scoped>

</style>