<template v-show="!detailStore.loading">
  <div class="pt-md-14">

    <div style="float: right">
      <dropdown-button v-if="role === 'ROLE_ADMIN'"
                       :delete-function="deleteFunction"
                       :edit-function="editFunction"
                       :lock-function="lockFunction"/>
      <dropdown-button v-else-if="authenticated"
                       :delete-function="deleteFunction"
                       :edit-function="editFunction"/>
      <dropdown-button v-else :disabled="true"/>
    </div>

    <v-container class="pr-0 pl-2 pl-sm-2 pl-md-4">
      <v-row class="pl-n6">

        <v-col cols="12" lg="4" md="4" sm="12" class="d-flex justify-md-center">
          <ProfilePic v-if="detailStore.profilePic"
                      :pic="detailStore.profilePic"/>
          <ProfilePic v-else
                      :initals="`${detailStore.details.getFirstName()[0]}${detailStore.details.getLastName()[0]}`"/>
        </v-col>

        <v-col cols="12" lg="8" md="8" sm="12" class="pa-0 pl-md-3 pl-sm-0 pr-0">
          <v-container class="pa-2 pr-0">

            <v-row>
              <v-col cols="12" lg="12" md="12" sm="12">
                <h1> {{ detailStore.details.getFirstName() }} {{ detailStore.details.getLastName() }}</h1>
                <h3> {{ detailStore.details.getJobTitle() }}, {{ detailStore.details.getAge() }}</h3>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" lg="8" md="8" sm="12" align-self="start">
                <info-with-icon icon="mdi-email"
                                :info="detailStore.details.getEmail()"/>
                <info-with-icon class="mt-3" icon="mdi-crown-outline"
                                :info="detailStore.details.getPrimarySkill()"/>
              </v-col>
              <v-col cols="12" lg="4" md="4" sm="12" align-self="end">
                <info-with-icon icon="mdi-phone"
                                :info="detailStore.details.getPhoneNumber()"/>
                <info-with-icon class = "mt-3" icon="mdi-cake-variant-outline"
                                :info="detailStore.details.getBirthDate()"/>
              </v-col>
            </v-row>

          </v-container>
        </v-col>
      </v-row>

      <v-row class="pt-6 pl-md-6 d-md-flex justify-md-center align-content-stretch">
        <v-col cols="12" lg="4" md="5" sm="12"
               class="d-md-flex flex-md-column align-md-center pa-sm-3 ml-0 pl-5">

          <InfoInCard info="Skills"
                      :content="detailStore.details.getSkills()"
                      :multiple="true" :chips="true" />
          <InfoInCard class="mt-3"
                      info="Abschluss"
                      :content="detailStore.details.getDegree()"/>
        </v-col>

        <v-col cols="12" lg="8" md="7" sm="12" class="pl-md-2 pl-5 pr-0">
          <div class="references pt-0 ">
            <div class="block_title">
              Referenzen
            </div>
            <div class="block_content">
              <ul class="pl-6">
                <li v-for="reference in detailStore.details.getReferences().split('\n')">
                  <p>{{ reference }}</p>
                </li>
              </ul>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import DropdownButton from "@/components/DetailComponents/DropdownButton.vue";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import router from "@/router";
import {useUserStore} from "@/stores/UserStore";
import InfoWithIcon from "@/components/DetailComponents/InfoWithIcon.vue";
import InfoInCard from "@/components/DetailComponents/InfoInCard.vue";
import ProfilePic from "@/components/DetailComponents/ProfilePic.vue";

export default {
  name: "DetailComponent",
  components: { InfoWithIcon, InfoInCard, ProfilePic, DropdownButton },
  async mounted() {
      const role = window.localStorage.getItem('role');
      if (role === "ROLE_HR") {
        this.authenticated = true;
      } else if (role === "ROLE_USER") {
        let userStore = useUserStore();
        const userId = window.localStorage.getItem('user_id');
        await userStore.checkForExistingUserProfile(userId);
        if (!userStore.hasProfile) {
          this.authenticated = false;
        } else {
          const profileId = String(this.$route.params.id);
          await userStore.getProfileIdFromUser(userId);
          this.authenticated = (String(userStore.profileId) === profileId);
        }
      } else {
        this.authenticated = false;
      }
  },
  data() {
    const detailStore = useDetailStore();
    const id = useRoute().params.id.toString();
    detailStore.loadDetailsById(id);

    const role = window.localStorage.getItem('role');
    const editFunction = {name: 'Bearbeiten', method: this.enterEdit};
    const deleteFunction = {name: 'Löschen', method: this.deleteProfile};
    const lockFunction = {name: 'Sperren', method: this.lockProfile};

    return {
      deleteFunction, editFunction, lockFunction, role, id,
      detailStore, authenticated: false
    };
  },
  methods: {
    enterEdit(): void {
      router.push({name: 'editView', params: {id: this.id}});
    },
    async lockProfile(): Promise<void> {
      await this.detailStore.loadDetailsById(this.id);
      const userId = this.detailStore.details.getOwnerId();
      const userStore = useUserStore();
      await userStore.loadUserById(userId);
      if (!userStore.user.getLocked()) {
        await userStore.lockUser(userId);
      }
    },
    deleteProfile(): void {
      this.detailStore.deleteDetailsByID(this.id);
      router.push(`/`);
    }
  }
}
</script>

<style scoped>

.block_title {
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 2px;
  font-family: "Poppins", sans-serif;
}

.block_content {
  margin-top: 12px;
  font-family: "Poppins", sans-serif;
}
</style>
