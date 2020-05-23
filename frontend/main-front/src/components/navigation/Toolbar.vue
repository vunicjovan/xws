<template>
    <div>
        <md-toolbar class="md-primary" md-elevation="1">
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