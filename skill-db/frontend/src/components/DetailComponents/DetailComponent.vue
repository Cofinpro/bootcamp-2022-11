<template>
  <div class="pt-md-14">

    <div style="float: right">
      <dropdown-button @delete="state.deleteProfile()"
                       @edit="state.enterEdit()"
                       @lock="state.lockProfile()"/>
    </div>

    <v-container class="pr-0 pl-2 pl-sm-2 pl-md-4">
      <v-row class="pl-n6">

        <v-col cols="12" lg="4" md="4" sm="12" class="d-flex justify-md-center">
          <ProfilePic :pic="state.profilePic"
                      :firstName="state.details.getFirstName()"
                      :lastName="state.details.getLastName()"/>
        </v-col>

        <v-col cols="12" lg="8" md="8" sm="12" class="pa-0 pl-md-3 pl-sm-0 pr-0">
          <v-container class="pa-2 pr-0">

            <v-row>
              <v-col cols="12" lg="12" md="12" sm="12">
                <h1> {{ state.details.getFirstName() }} {{ state.details.getLastName() }}</h1>
                <h3> {{ state.details.getJobTitle() }}, {{ state.details.getAge() }}</h3>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" lg="8" md="8" sm="12" align-self="start">
                <info-with-icon icon="mdi-email"
                                :info="state.details.getEmail()"/>
                <info-with-icon class="mt-3" icon="mdi-crown-outline"
                                :info="state.details.getPrimarySkill()"/>
              </v-col>
              <v-col cols="12" lg="4" md="4" sm="12" align-self="end">
                <info-with-icon icon="mdi-phone"
                                :info="state.details.getPhoneNumber()"/>
                <info-with-icon class = "mt-3" icon="mdi-cake-variant-outline"
                                :info="state.details.getBirthDate()"/>
              </v-col>
            </v-row>

          </v-container>
        </v-col>
      </v-row>

      <v-row class="pt-6 pl-md-6 d-md-flex justify-md-center align-content-stretch">
        <v-col cols="12" lg="4" md="5" sm="12"
               class="d-md-flex flex-md-column align-md-center pa-sm-3 ml-0 pl-5">

          <InfoInCard info="Skills"
                      :content="state.details.getSkills()"
                      :multiple="true" :chips="true" />
          <InfoInCard class="mt-3"
                      info="Abschluss"
                      :content="state.details.getDegree()"/>
        </v-col>

        <v-col cols="12" lg="8" md="7" sm="12" class="pl-md-2 pl-5 pr-0">
          <References :content="state.details.getReferences()"/>
        </v-col>

      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import DropdownButton from "@/components/DetailComponents/DropdownButton.vue";
import InfoWithIcon from "@/components/DetailComponents/InfoWithIcon.vue";
import InfoInCard from "@/components/DetailComponents/InfoInCard.vue";
import ProfilePic from "@/components/DetailComponents/ProfilePic.vue";
import {DetailComponentState} from "@/components/DetailComponents/DetailComponentState";
import References from "@/components/DetailComponents/References.vue";
import {ref} from "vue";

export default {
  name: "DetailComponent",
  components: {References, InfoWithIcon, InfoInCard, ProfilePic, DropdownButton },
  setup() {
    const state = ref(new DetailComponentState());
    state.value.loadDetailsById(state.value.id);

    return {
      state
    };
  }
}
</script>

<style scoped>
</style>
