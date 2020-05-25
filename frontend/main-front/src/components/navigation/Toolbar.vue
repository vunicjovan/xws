<template>
    <div>
        <md-toolbar class="md-default" md-elevation="1">
            <router-link to="/">
              <span class="md-layout" :class="`md-alignment-center-center`">
                  <img class="md-layout-item" alt="Vue logo" src="../../assets/logo1.png">
                  <div><p class="md-layout-item md-title a">RentaSoul</p></div>
              </span>
            </router-link>
            <div style="flex: 1;"></div>
            <md-button v-if="!isLogged" to="/login">Login</md-button>
            <md-button v-if="!isLogged" to="/register">Register</md-button>
            <md-button v-if="isLogged" v-on:click="logout()">Logout</md-button>
        </md-toolbar>
    </div>
</template>

<script>
import { EventBus } from '../../event-bus.js'

export default {
    name: "Toolbar",
    mounted: function() {
        if (localStorage.getItem("auth")) this.isLogged = true;
        else this.isLogged = false;

        EventBus.$on("logged", logged => { this.isLogged = logged });
    },

    data: function() {
        return {
            isLogged: undefined
        }
    },

    methods: {
        logout() {
            this.isLogged = false;
            localStorage.removeItem("auth");
        }
    }
};
</script>

<style scoped>
 a {
     text-decoration: none !important;
 }
</style>