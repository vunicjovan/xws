<template>
	<div>
		<transition name="fade">
			<span v-if="show" class="md-layout md-alignment-top-center md-headline">Welcome to RentaSoul service</span>
		</transition>
		<transition name="fade2">
			<span v-if="show2" class="md-layout md-alignment-top-center md-subheading">A place for your advertisements</span>
		</transition>
	</div>
</template>

<script>
export default {
	name: "HelloWorld",
	props: {
		msg: String,
	},
	data: function() {
		return {
			show: false,
			show2: false
		};
	},
	mounted: function() {
		this.fadeMe();
		this.fadeMe2();
	},
	methods: {
		fadeMe: function() {
			this.show = !this.show;
		},
		fadeMe2: function() {
			this.show2 = !this.show2;
		}
	},
};
</script>

<style scoped>
	.fade-enter-active,
	.fade-leave-active {
		transition: opacity 2.5s;
	}

	.fade2-enter-active,
	.fade2-leave-active {
		transition: opacity 4.5s;
	}

	.fade-enter,
	.fade-leave-to,
	.fade2-enter,
	.fade2-leave-to {
		opacity: 0;
	}

	/*.md-layout {
		font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif
	}*/
</style>
