// 获取所有输入框和标签元素
const inputDivs = document.querySelectorAll('.input-div');
const inputs = document.querySelectorAll('.input-div input');

// 添加事件监听器以检测输入框的焦点状态
inputs.forEach(input => {
  input.addEventListener('focus', focusFunc);
  input.addEventListener('blur', blurFunc);
});

function focusFunc() {
  let parent = this.parentNode.parentNode;
  parent.classList.add('focus');
}

function blurFunc() {
  let parent = this.parentNode.parentNode;
  if (this.value === '') {
    parent.classList.remove('focus');
  }
}
