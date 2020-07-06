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

	async deleteMessage(message) {
		try {
			const response = await axios.delete(`/message/${message.id}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
