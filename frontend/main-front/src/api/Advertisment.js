import axios from "axios";

export default {
	postAdvertisment(advertisment) {
		return axios.post("/agent/ad/", advertisment).then((response) => {
			return response.data;
		});
	},
};
