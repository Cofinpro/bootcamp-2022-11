<template>
    <v-navigation-drawer v-model="drawer" app temporary>
      <v-list>
        <v-list-item>
          <v-list-item-title>
            <span>Menu</span>
          </v-list-item-title>
        </v-list-item>
        <v-divider></v-divider>
        <template v-for="(item, index) in items">
          <v-list-item :href="item.href" :to="{name: item.href}" :key="index">
            <v-list-item-action>
              <v-icon light v-html="item.icon"></v-icon>
            </v-list-item-action>
            <v-list-item>
              <v-list-item-title v-html="item.title"></v-list-item-title>
            </v-list-item>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar class="indigo" dark app>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Sidebar template</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon @click.native.stop="openGithub()">
        <v-icon>home</v-icon>
      </v-btn>
    </v-toolbar>
    <v-footer class="indigo pa-3" dark app>
      <span>© 2022 - <a href="#" class="indigo text--lighten-5">Bootcamp</a></span>
    </v-footer>
</template>

<script type="text/babel">
export default {
  data() {
    return {
      drawer: false,
      items: [{
        href: 'home',
        router: true,
        title: 'Home',
        icon: 'home',
      }, {
        href: 'examples',
        router: true,
        title: 'Example',
        icon: 'extension',
      }, {
        href: 'about',
        router: true,
        title: 'About',
        icon: 'domain',
      }],
    };
  },
  methods: {
    openGithub() {
      window.open('https://github.com/disjfa/vuetify-sidebar-template');
    },
  },
};

export class SideBar {
}
</script>

<style lang="stylus">
</style>