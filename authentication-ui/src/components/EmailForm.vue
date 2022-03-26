<template>
  <form @submit="onSubmit" class="email-form">
    <div class="form-control">
      <label>Email</label>
      <input type="email" v-model="email" name="email" placeholder="Email" />
    </div>

    <input type="submit" value="Send Email" class="btn" />
  </form>           
</template>

<script>
export default {
    name: 'EmailForm',
    data() {
        return {
            email: ""
        }
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()

            this.$emit('sendemail', this.email)

            this.email = ""
        }
    }
}
</script>