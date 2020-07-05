import axios from "axios";

export default {
	async getPriceList() {
		try {
			const response = await axios.get("/priceList/");
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async postPriceListItem(pricelistItem) {
		try {
			const response = await axios.post("/priceList/", pricelistItem);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
