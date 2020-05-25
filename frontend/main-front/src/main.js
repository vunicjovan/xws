import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueResource from "vue-resource";
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default-dark.css'

Vue.use(VueMaterial)

Vue.prototype.axios = axios
Vue.config.productionTip = false

Vue.use(VueResource);

axios.interceptors.request.use((config) => {
	const authToken = localStorage.getItem('auth')
	if (authToken)
		config.headers['Authorization'] = authToken;
	return config;
}, (error) => {
	Promise.reject(error)
	return error;
});

axios.interceptors.response.use((response) => {

	return response;
}, (error) => {
	return Promise.reject(error.message);
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
