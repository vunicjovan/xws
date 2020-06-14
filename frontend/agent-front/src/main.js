import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import VueResource from "vue-resource";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.min.css";
import "vue-material/dist/theme/default-dark.css";
import store from "./store";

import '@fortawesome/fontawesome-free/css/all.css';
import '@fortawesome/fontawesome-free/js/all.js';


Vue.use(VueMaterial);
Vue.use(VueResource);

axios.defaults.baseURL = "http://localhost:8089";

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
