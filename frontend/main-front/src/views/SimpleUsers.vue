<template>
  <div v-if="getSimpleUsers">
    <md-card v-for="user in getSimpleUsers" :key="user.id" class="md-layout-item md-xlarge-size-20 md-large-size-20 md-medium-size-40 md-small-size-50 md-xsmall-size-90">
      <md-card-header>
        <md-card-header-text>
            <div class="md-title">{{ user.firstName}} {{user.lastName}}</div>
            <div class="md-subhead">
                <span v-if="user.numberOfCancelations === 3" class="warning">Number of cancelations: {{ user.numberOfCancelation }}</span>
                <span v-else-if="user.numberOfCancelations > 2" class="block">Number of cancelations: {{ user.numberOfCancelation }}</span>
                <span v-else>Number of cancelations: {{ user.numberOfCancelation }}</span>
            </div>
        </md-card-header-text>
      </md-card-header>

      <md-card-actions>
        <md-button @click="blockUser(user.id)" class="md-accent" v-if="user.blocked">Unblock</md-button>  
        <md-button @click="blockUser(user.id)" class="md-accent" v-else>Block</md-button>
        <md-button @click="deleteUser(user.id)" class="md-raised md-accent">Remove</md-button>
      </md-card-actions>
    </md-card>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex"
export default {
    name: "SimpleUsers",
    data() {
        return {
            users: [
                {
                    id: 1,
                    firstName: "Petar",
                    lastName: "Petrovic",
                    numberOfCancelations: 1,
                    blocked: false,
                },
                {
                    id: 2,
                    firstName: "Marko",
                    lastName: "Markovic",
                    numberOfCancelations: 2,
                    blocked: false,
                },
                {
                    id: 3,
                    firstName: "Zeljko",
                    lastName: "Zeljkovic",
                    numberOfCancelations: 0,
                    blocked: false,
                },
                {
                    id: 4,
                    firstName: "Srdjan",
                    lastName: "Srdjanovic",
                    numberOfCancelations: 4,
                    blocked: false,
                },
                {
                    id: 5,
                    firstName: "Goran",
                    lastName: "Goranovic",
                    numberOfCancelations: 0,
                    blocked: true,
                }
            ]
        }
    },
    mounted() {
        this.$store.dispatch("getSimpleUsers")
    },
    computed: {
        ...mapGetters(["getSimpleUsers"])
    },
    methods: {
        ...mapActions(["deleteUser", "blockUser"]),
    }

}
</script>

<style>
    .warning {
        color: darkorange;
    }

    .block {
        color: red;
    }

</style>