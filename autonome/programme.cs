using System;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using System.IO;
using QRCoder;
class Program
{
    static async Task Main(string[] args)
    {
        using HttpClient client = new HttpClient();
        string api_url = "http://localhost:8080/medecine/api/patients";
        HttpResponseMessage response = await client.GetAsync(api_url);
        string responseBody = await response.Content.ReadAsStringAsync();
        Patient[]? patients = JsonSerializer.Deserialize<Patient[]>(responseBody);

        if (patients == null)
        {
            Console.WriteLine("Aucun patient récupéré.");
            return;
        }

        foreach (Patient p in patients)
        {
            string qrCode_contenu = $"http://localhost:77/patient/[{p.id}]";
            QRCodeGenerator generateur = new QRCodeGenerator();
            QRCodeData données = generateur.CreateQrCode(
                qrCode_contenu, QRCodeGenerator.ECCLevel.Q
            );
            PngByteQRCode qrCode = new PngByteQRCode(données);
            byte[] qrCode_image = qrCode.GetGraphic(20);
            String nomFichier = Path.Combine("/usr/local/cdn/patient/", $"[{p.id}]", "qrcode", $"[{p.id}]-qrcode.png");
            String nomDossier = Path.GetDirectoryName(nomFichier)!;
            if(nomDossier != null && !Directory.Exists(nomDossier))
            {
                Directory.CreateDirectory(nomDossier);
            }
            File.WriteAllBytes(nomFichier, qrCode_image);
            Console.WriteLine($"QR code du patient {p.id} généré.");
        }
    }
}