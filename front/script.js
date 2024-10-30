async function applyProcessing() {
    const form = document.getElementById('imageForm');
    const formData = new FormData(form);

    const process = document.getElementById('process').value;
    const url = `http://localhost:8080/api/image/${process}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            body: formData
        });

        if (!response.ok) throw new Error('Ошибка обработки изображения');

        const filename = await response.text();
        console.log('Filename:', filename);

        const imageUrl = `http://localhost:8080/output/${filename}`;
        document.getElementById('outputImage').src = imageUrl;

    } catch (error) {
        console.error(error);
        alert(error.message);
    }
}

function toggleParameters() {
    document.querySelectorAll('.parameter').forEach(param => param.style.display = 'none');

    const selectedProcess = document.getElementById('process').value;
    if (selectedProcess === 'linear-contrast') {
        document.getElementById('alphaBeta').style.display = 'block';
    } else if (selectedProcess === 'local-threshold-mean' || selectedProcess === 'local-threshold-gaussian') {
        document.getElementById('blockSize').style.display = 'block';
    } else if (selectedProcess === 'adaptive-threshold') {
        document.getElementById('blockSize').style.display = 'block';
        document.getElementById('constant').style.display = 'block';
    }
}

function validateBlockSize(event) {
    const blockInput = document.getElementById('block');
    const errorMessage = document.getElementById('error-message');
    const blockSize = Number(blockInput.value);
    const selectedProcess = document.getElementById('process').value;

    if (selectedProcess === 'local-threshold-gaussian') {
        if (blockSize % 2 !== 0 && blockSize >= 1) {
            errorMessage.style.display = 'none';
        } else {
            errorMessage.style.display = 'block';
            event.preventDefault();
        }
    } else {
        errorMessage.style.display = 'none';
    }
}

document.getElementById('process').addEventListener('change', toggleParameters);
document.getElementById('block').addEventListener('input', validateBlockSize);

document.addEventListener('DOMContentLoaded', toggleParameters);
