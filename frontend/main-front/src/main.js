import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "axios";
import VueResource from "vue-resource";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import "vue-material/dist/theme/default-dark.css";
import store from "./store";

import '@fortawesome/fontawesome-free/css/all.css'
import '@fortawesome/fontawesome-free/js/all.js'

Vue.use(VueMaterial);

Vue.use(VueResource);

axios.defaults.baseURL = "http://localhost:8089";

axios.interceptors.request.use(
	(config) => {
		const authToken = localStorage.getItem("auth");
		if (authToken) config.headers["Authorization"] = authToken;
		return config;
	},
	(error) => {
		Promise.reject(error);
		return error;
	}
);

axios.interceptors.response.use(
	(response) => {
		return response;
	},
	(error) => {
		return Promise.reject(error.message);
	}
);

Vue.config.productionTip = false;

new Vue({
	router,
	store,
	render: (h) => h(App),
}).$mount("#app");
