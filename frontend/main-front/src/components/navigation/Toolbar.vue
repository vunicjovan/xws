<template>
	<div>
		<md-toolbar class="md-default" md-elevation="1">
			<router-link to="/">
				<span class="md-layout" :class="`md-alignment-center-center`">
					<img class="md-layout-item" alt="Vue logo" src="../../assets/logo1.png" />
					<div><p class="md-layout-item md-title a">RentaSoul</p></div>
				</span>
			</router-link>
			<div style="flex: 1;"></div>
			<md-button to="/catalog">Catalog</md-button>
			<md-button v-if="!isLogged" @click.native="$router.push('/login')">Login</md-button>
			<md-button v-if="!isLogged" @click.native="$router.push('/register')">Register</md-button>
			<md-button v-if="isLogged" @click="logout()">Logout</md-button>
		</md-toolbar>
	</div>
</template>

<script>
//import { EventBus } from "../../event-bus.js";
import { mapGetters, mapActions } from "vuex";
export default {
	name: "Toolbar",
	created() {
		if (localStorage.getItem("auth")) {
			this.$store.dispatch("setLogged", true);
		}
	},
	computed: {
		...mapGetters(["isLogged"]),
	},
	methods: {
		...mapActions(["setLogged", "logout"]),
		logout() {
			this.$store.dispatch("logout");
		},
	},
};
</script>

<style scoped>
a {
	text-decoration: none !important;
}
</style>
