<template>
	<div id="app">
		<div id="nav">
			<Toolbar />
		</div>
		<div id="body">
			<router-view />
		</div>
	</div>
</template>

<script>
import { mapActions } from "vuex";

export default {
	created() {
		if (localStorage.getItem("auth")) {
			this.$store.dispatch("setLogged", true);
			this.$store
				.dispatch("logged")
				.then(() => {})
				.catch((error) => {
					console.log(error);
				});
		}
	},
	components: {
		Toolbar: () => import("./components/navigation/Toolbar.vue"),
	},
	methods: mapActions(["setLogged", "logged"]),
};
</script>

<style>
#body {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	padding: 15px;
	color: white;
}
</style>
