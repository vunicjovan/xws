import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "axios";
import VueResource from "vue-resource";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import "vue-material/dist/theme/default-dark.css";
import store from "./store";

import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";

import VueFlashMessage from 'vue-flash-message';
require('vue-flash-message/dist/vue-flash-message.min.css');

Vue.use(VueFlashMessage);
Vue.use(VueMaterial);
Vue.use(VueResource);

axios.defaults.baseURL = "http://localhost:8091";

axios.interceptors.response.use(
	(response) => {
		return response;
	},
	(error) => {

		const {
			config,
			response: { status, data },
		} = error;

		return Promise.reject(data);
	}
);

Vue.config.productionTip = false;

new Vue({
	router,
	store,
	render: (h) => h(App),
}).$mount("#app");
