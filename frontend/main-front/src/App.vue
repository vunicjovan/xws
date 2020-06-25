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
		
		((nm,tm) => {
			const
			l = localStorage,
			s = sessionStorage,
			tabid = s.getItem(tm) || (newid => s.setItem(tm, newid) || newid)((Math.random() * 1e8).toFixed()),
			update = set => {
				let cur = JSON.parse(l.getItem(nm) || '{}');
				if (set && typeof cur[tabid] == 'undefined' && !Object.values(cur).reduce((a, b) => a + b, 0)) {
					l.removeItem('tabs');
					l.removeItem('auth')
					cur = {};
				}
				cur[tabid] = set;
				l.setItem(nm, JSON.stringify(cur));
			};
			update(1);
			window.onbeforeunload = () => update(0);
		})('tabs','tabid');
					
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
