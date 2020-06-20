import axios from "axios";

export default {

    getMessages(userId) {
		return axios.get(`/message/${userId}`).then((response) => {
			return response.data;
		});
	},

}
