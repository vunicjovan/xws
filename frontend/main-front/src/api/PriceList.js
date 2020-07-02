import axios from "axios";

export default {
	async getUsersPriceList(userId) {
		try {
			const response = await axios.get(`/agent/price/list/${userId}`);

			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async postPriceListItem(pricelistItem) {
		try {
			const response = await axios.post("/agent/price/list/", pricelistItem);

			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
