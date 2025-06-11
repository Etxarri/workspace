document.addEventListener("DOMContentLoaded", function ()
{
    const ctx = document.getElementById("graficoPolar").getContext("2d");

    new Chart(ctx, {
        type: "polarArea",
        data: {
            labels: etiquetas,
            datasets: [{
                label: "Indice de integración",
                data: datosGrafico,
                backgroundColor: [
                    "rgba(255, 206, 86, 0.5)", //Psicológica
                    "rgba(153, 102, 255, 0.5)", //Lingüística
                    "rgba(75, 192, 192, 0.5)", //Económica
                    "rgba(54, 162, 235, 0.5)", //Política
                    "rgba(255, 99, 132, 0.5)", //Social
                    "rgba(255, 159, 64, 0.5)" //Navegacional
                ],
                borderColor: [
                    "rgb(255, 206, 86)",
                    "rgb(153, 102, 255)",
                    "rgb(75, 192, 192)",
                    "rgb(54, 162, 235)",
                    "rgb(255, 99, 132)",
                    "rgb(255, 159, 64)"
                ],
                borderAlign: "inner",
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 1,
            scales: {
                r: {
                    min: 0,
                    max: 1,
                    beginAtZero: true,
                    pointLabels: {
                        centerPointLabels: true,
                        display: true,
                        font: {
                            family: "'Poppins', sans-serif",
                            size: 16//Math.max(16, Math.min(window.innerWidth * 0.025, 32))
                        }
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
