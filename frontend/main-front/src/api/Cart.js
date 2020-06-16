import axios from "axios";

export default {
	getCart(cartId) {
		return axios.get(`/rent/cart/${cartId}`).then((response) => {
			return response.data;
		});
	},

	getCartAdvertisements(params) {
		return axios.get("/view/cart", { params }).then((response) => {
			return response.data;
		});
	},

	addCartItem(userId, advetrisementId) {
		return axios.post(`/rent/cart/${userId}/item/${advetrisementId}`).then((response) => {
			return response.data;
		});
	},

	deleteCartItem(cartId, advetrisementId) {
		return axios.delete(`/rent/cart/${cartId}/item/${advetrisementId}`).then((response) => {
			return response.data;
		});
	},

	async sendRentingRequest(payload) {
		try {
			const response = await axios.post("/rent/request/", payload);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
