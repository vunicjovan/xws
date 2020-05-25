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
            <md-button to="/login">Login</md-button>
            <md-button to="/register">Register</md-button>
        </md-toolbar>
    </div>
</template>

<script>
export default {
    name: "Toolbar"
};
</script>

<style scoped>
 a {
     text-decoration: none !important;
 }
</style>