import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import axios from "axios";
import VueResource from "vue-resource";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import "vue-material/dist/theme/default-dark.css";
import store from "./store";
import VueChatScroll from "vue-chat-scroll";

import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";

Vue.use(VueMaterial);
Vue.use(VueResource);
Vue.use(VueChatScroll);

axios.defaults.baseURL = "https://localhost:8089";
axios.defaults.withCredentials = true;
axios.defaults.credentials = "include";

let refreshing = false;
let waitingActions = [];

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
		const {
			config,
			response: { status, data },
		} = error;

		const requestInWait = config;

		if (status === 401 && data === "Token has expired!") {
			if (!refreshing) {
				refreshing = true;
				store
					.dispatch("refreshToken")
					.then(({ status }) => {
						if (status === 200) {
							refreshing = false;
						}
						executeActions();
						waitingActions = [];
					})
					.catch((error) => {
						store.dispatch("logout");
						router.push("/login");
					});
			}

			const requestAction = new Promise((resolve) => {
				appendAction(() => {
					resolve(axios(requestInWait));
				});
			});

			return requestAction;
		}

		return Promise.reject(error);
	}
);

function appendAction(action) {
	waitingActions.push(action);
}

function executeActions() {
	waitingActions.map((action) => action());
}

waitingActions = [];

Vue.config.productionTip = false;

new Vue({
	router,
	store,
	render: (h) => h(App),
}).$mount("#app");
