import axios from "axios";

export default {
	getMessages() {
		return axios.get(`/message/`).then((response) => {
			return response.data;
		});
	},

	sendMessage(message) {
		return axios.post(`/message/`, message).then((response) => {
			return response.data;
		});
	},
};
