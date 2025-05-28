document.addEventListener("DOMContentLoaded", function ()
{
    const ctx = document.getElementById("graficoPolar").getContext("2d");

    new Chart(ctx, {
        type: "polarArea",
        data: {
            labels: etiquetas,
            datasets: [{
                label: "Puntajes de Integración",
                data: datos,
                backgroundColor: [
                    "rgba(255, 99, 132, 0.5)",
                    "rgba(54, 162, 235, 0.5)",
                    "rgba(255, 206, 86, 0.5)",
                    "rgba(75, 192, 192, 0.5)",
                    "rgba(153, 102, 255, 0.5)",
                    "rgba(255, 159, 64, 0.5)"
                ],
                borderColor: "#333",
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                r: {
                    max: 5,
                    beginAtZero: true,
                    pointLabels: {
                        centerPointLabels: true,
                        display: true
                    }
                }
            },
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
});
