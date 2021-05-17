class RandomUtil {
}
RandomUtil.getRandomNumber = (min, max) => parseInt(Math.random() * (max - min)) + min
module.exports = RandomUtil
