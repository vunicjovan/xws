<template>
  <div>
    <transition name="fade">
      <span v-if="show" class="md-layout md-alignment-top-center md-headline">Welcome to RentaSoul service</span>
    </transition>
      <md-button v-on:click="s()" class="md-primary">Delete</md-button>
      <md-button v-on:click="a()" class="md-primary">Create</md-button>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data: function() {
    return {
      show: false
    }
  },
  mounted: function() {
    this.fadeMe();
  },
  methods: {
    fadeMe: function() {
      this.show = !this.show
    },

    s: function() {
      alert(localStorage.getItem('auth'))
      this.axios.delete("https://localhost:8089/account/1")
                      .then(() => {
                          alert('Deleted')
                      })
                      .catch(() => {
                          alert('failed!');
                      })
    },

    a: function() {
      this.axios.put("https://localhost:8089/account/1")
                      .then(() => {
                          alert('Created')
                      })
                      .catch(() => {
                          alert('failed!');
                      })
    }
  }
}
</script>

<style scoped>
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 2.5s
  }

  .fade-enter,
  .fade-leave-to {
    opacity: 0
  }
</style>
