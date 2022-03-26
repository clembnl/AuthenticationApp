<template>
  <form @submit="onSubmit" class="password-form">
    <div class="form-control">
      <label>Password</label>
      <input type="password" v-model="password" name="password" placeholder="Password" />
    </div>      

    <input type="submit" value="Reset Password" class="btn" />
  </form>           
</template>

<script>
export default {
    name: 'PasswordForm',
    data() {
        return {
            password: "",
        }
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()

            this.$emit('resetpassword', this.password)

            this.password = ""
        }
    }
}
</script>