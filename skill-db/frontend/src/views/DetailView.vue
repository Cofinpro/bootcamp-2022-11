<template>
  <v-row class="header">
    <v-col cols="3">
      <div class="icon"></div>
    </v-col>
    <v-col class="d-flex flex-column header_content">
      <v-row class="h-100">
        <v-col>
          <h1> {{ detail.getFirstName() }} {{ detail.getLastName() }}</h1>
          <h3>{{ detail.getJobTitle() }}, {{ detail.getAge() }}</h3>
        </v-col>
        <v-col cols="1">
          <v-menu :close-on-content-click="false">
            <template v-slot:activator="{ props }">
              <v-btn v-bind="props"
                     min-width="40px" width="40px" height="35px"
                     class="pa-0" elevation="0">
                <v-icon size="large">
                  mdi-cog
                </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item link>
                <v-list-item-title @click="toggleEdit"> Bearbeiten</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title @click="deleteProfile"> Löschen</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title @click="lockProfile"> Sperren</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-col>
      </v-row>
      <div class="d-flex w-50 justify-space-between">
        <div class="d-flex flex-column">
          <p>
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-email</v-icon>
            max.mustermann@cofinpro.de
          </p>
          <p class="mt-3">
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-crown-outline</v-icon>
            {{ detail.getPrimarySkill() }}
          </p>
        </div>
        <div class="d-flex flex-column">
          <p>
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-phone</v-icon>
            +49 176 65544 000
          </p>
          <p class="mt-3">
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-cake-variant-outline</v-icon>
            {{ detail.getBirthDate() }}
          </p>
        </div>
      </div>
    </v-col>
  </v-row>
  <v-row class="pl-6 pr-6">
    <v-col cols="3">
      <div>
        <div class="content_card">
          <div class="block_title">Skills</div>
          <SearchableMultiSelectDropdown :options="myOptions"/>
          <div class="d-flex">
            <p v-for="skill in detail.getTechnologies()" class="pa-1 flex-wrap">
              <v-chip>{{ skill }}</v-chip>
            </p>
          </div>
        </div>
        <div class="content_card mt-5">
          <div class="block_title">Abschluss</div>
          <div class="block_content">
            B.Sc. Wirtschaftinformatik
          </div>
        </div>
      </div>
    </v-col>
    <v-col class="w-100">
      <SearchableDropdown/>
      <div class="pl-6 pt-2">
        <div class="block_title">Referenzen</div>
        <div class="block_content">
          <ul class="pl-6">
            <li v-for="reference in detail.getReferences()">
              <p>{{ reference }}</p>
            </li>
          </ul>
        </div>
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import SearchableMultiSelectDropdown from "@/components/SearchableMultiSelectDropdown.vue";

export default {
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    const detail = detailStore.details;

    let showSettings: boolean = false;
    let editMode: boolean = false;
    let locked: boolean = false;

    return {
      detail, showSettings,
      editMode, locked
    };
  },
  methods: {
    toggleEdit(): void {
      this.editMode = !this.editMode;
      console.log("Now you can edit.");
    },
    lockProfile(): void {
      this.locked = !this.locked;
      console.log("This profile is now locked away.");
    },
    deleteProfile(): void {
      /*ask a second time, before delete*/
      console.log("You want to delete this profile? OK.")
    },
  },
  data() {
    return {
      myOptions: [
        {value: 1, text: 'Option 1'},
        {value: 2, text: 'Option 2'},
        {value: 3, text: 'Option 3'}
      ],
    }
  },
  components: {
    SearchableMultiSelectDropdown: SearchableMultiSelectDropdown
  }
}
</script>

<style scoped>
.header {
  display: flex;
  max-width: 100%;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 48px 24px;
  background-color: rgba(255, 255, 255, 1);
}

.header_content {
  height: 256px;
}

.icon {
  display: flex;
  height: 256px;
  border-radius: 12px;
  background-color: rgba(196, 196, 196, 1);
}

.content_card {
  display: flex;
  flex-direction: column;
  max-width: 100%;
  justify-content: flex-start;
  align-items: flex-start;
  border-radius: 8px;
  padding: 8px 12px;
  border-color: rgba(217, 217, 217, 1);
  border-width: 1px;
  border-style: solid;
  background-color: rgba(255, 255, 255, 1);
  width: 100%;
}

.block_title {
  max-width: 100%;
  color: rgba(0, 0, 0, 1);
  font-size: 16px;
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 4px;
  text-align: left;
  font-family: "Poppins", sans-serif;
  width: 100%;
}

.block_content {
  margin-top: 12px;
  font-size: 16px;
  text-align: left;
  font-family: "Poppins", sans-serif;
}

</style>
