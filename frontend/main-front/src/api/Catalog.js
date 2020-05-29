import axios from "axios";

export default {
	getCatalog() {
		return axios.get("/catalog/").then((response) => {
			return response.data;
		});
	},
};
