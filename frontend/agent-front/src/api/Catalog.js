import axios from "axios";

export default {
	async getCatalog() {
		try {
			const response = await axios.get("/catalog/");
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
