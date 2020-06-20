import axios from "axios";

export default {

    getMessages(userId) {
		return axios.get(`/message/${userId}`).then((response) => {
			return response.data;
		});
	},

	sendMessage(message) {
		return axios.post(`/message/`, message).then((response) => {
			return response.data;
		});
	},

}
