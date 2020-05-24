<template>
    <div>
        <md-toolbar class="md-default" md-elevation="1">
            <router-link to="/"><img alt="Vue logo" src="../../assets/logo1.png"></router-link>
            <router-link class="md-title a" style="flex: 1" to="/">RentaSoul</router-link>
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