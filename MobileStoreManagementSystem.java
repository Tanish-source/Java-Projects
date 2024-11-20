import java.util.*;

class store {
        Scanner sc = new Scanner(System.in);

        String brandName = "";
        String varName = "";
        String type = "";
        int cost;
        String detail = "";
        int quantity;

        store(String brandName1, String varName1, String type1, int cost1, String detail1, int quantity1) {
                this.brandName = brandName1;
                this.varName = varName1;
                this.type = type1;
                this.cost = cost1;
                this.detail = detail1;
                this.quantity = quantity1;
        }

        store() {

        }

        void getData() {
                System.out.println();
                System.out.println("Brand Name : " + this.brandName);
                System.out.println("Model Name : " + this.varName);
                System.out.println("Product Name : " + this.type);
                System.out.println("Product cost : " + this.cost);
                System.out.println("Product details : " + this.detail);

                for (int l = 0; l <= 100; l++) {
                        System.out.print("_");
                }
                System.out.println();
                for (int l = 0; l <= 100; l++) {
                        System.out.print("_");
                }
        }

        boolean containsSubstring(String text, String substring) {
                return text.contains(substring);
        }

        void searchByDec(ArrayList<store> arr, String typ11) {

                System.out.print("Search : ");
                String s = sc.nextLine().toLowerCase();

                String[] sarr = s.split("\\s+");

                for (int i = 0; i < arr.size(); i++) {

                        if (arr.get(i).type.equals(typ11)) {

                                String ch = arr.get(i).brandName + arr.get(i).varName + arr.get(i).cost
                                                + arr.get(i).detail;
                                ch = ch.toLowerCase();
                                for (int j = 0; j < sarr.length; j++) {

                                        if (ch.contains(sarr[j])) {
                                                arr.get(i).getData();

                                                break;
                                        }

                                }

                        }

                }

        }

        void searchByRange(ArrayList<store> arr, String typ) {
                System.out.print("  ENTER STARTING RANGE : ");
                int s1 = sc.nextInt();
                System.out.println();
                System.out.print("  ENTER ENDING RANGE : ");
                int e1 = sc.nextInt();
                System.out.println();

                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        if (arr.get(i).type.equals(typ) && arr.get(i).cost <= e1 && arr.get(i).cost >= s1) {
                                arr.get(i).getData();

                        }
                }
        }

        static void print(ArrayList<store> arr, String atyp) {
                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        if (arr.get(i).type.toLowerCase().equals(atyp.toLowerCase())) {
                                arr.get(i).getData();

                        }
                }
        }

        void searchByName(ArrayList<store> arr, String typ2) {
                System.out.print("ENTER NAME OF PRODUCT: ");
                String productName = sc.nextLine().replaceAll("\\s", "").toLowerCase();
                System.out.println();

                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        String cN = (arr.get(i).brandName + arr.get(i).varName).replaceAll("\\s", "").toLowerCase();

                        if (productName.equals(cN) && arr.get(i).type.equals(typ2)) {

                                arr.get(i).getData();

                                System.out.println();
                                break;
                        }
                }
        }

        void offer(ArrayList<store> arr, int c) {
                System.out.println();
                System.out.print("  PRICE BEFORE WITHOUT OFF : " + arr.get(c).cost);
                System.out.println();
                System.out.print("  PRICE AFTER 10% OFF : " + (int) (arr.get(c).cost - (arr.get(c).cost / 10)));
                System.out.println();

                System.out.println("  PRESS 0 FOR BUY PRODUCT  ");
                int y = sc.nextInt();
                if (y == 0) {
                        System.out.println("*  THANKYOU FOR VISIT AND OWN PRODUCTS FROM OUR store  *");
                }
        }

        void selection(ArrayList<store> arr, String typ4) {
                System.out.println();

                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        if (arr.get(i).type.equalsIgnoreCase(typ4)) {
                                System.out.println("product number is " + i);
                                arr.get(i).getData();

                                for (int j = 0; j <= 100; j++) {
                                        System.out.print("_");
                                }
                                System.out.println();
                        }
                }

                System.out.println("  CHOOSE PRODUCT NUMBER TO BUY  ");
                int r = sc.nextInt();
                arr.get(0).offer(arr, r);
        }
}

class Main {
        public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);

                ArrayList<store> arr = new ArrayList<>();

                // Realme 7 Pro
                // Realme Phones from index 0 to 8
                arr.add(new store("Realme", "GT", "phone", 25999,
                                "Camera: Triple camera setup (64 MP + 8 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 870\n" +
                                                "Display: 6.43 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "8 Pro", "phone", 17999,
                                "Camera: Quad camera setup (108 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 720G\n" +
                                                "Display: 6.4 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "7 Pro", "phone", 19999,
                                "Camera: Quad camera setup (64 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 720G\n" +
                                                "Display: 6.4 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "Narzo 30 Pro", "phone", 16999,
                                "Camera: Triple camera setup (48 MP + 8 MP + 2 MP)\n" +
                                                "Processor: MediaTek Dimensity 800U\n" +
                                                "Display: 6.5 inches, IPS LCD, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "X7 Pro", "phone", 29999,
                                "Camera: Quad camera setup (64 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: MediaTek Dimensity 1000+\n" +
                                                "Display: 6.55 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "6 Pro", "phone", 17999,
                                "Camera: Quad camera setup (64 MP + 12 MP + 8 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 720G\n" +
                                                "Display: 6.6 inches, IPS LCD, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "C25", "phone", 9999,
                                "Camera: Triple camera setup (13 MP + 2 MP + 2 MP)\n" +
                                                "Processor: MediaTek Helio G70\n" +
                                                "Display: 6.5 inches, IPS LCD, 720 x 1600 pixels",
                                1));

                arr.add(new store("Realme", "Narzo 20 Pro", "phone", 14999,
                                "Camera: Quad camera setup (48 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: MediaTek Helio G95\n" +
                                                "Display: 6.5 inches, IPS LCD, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Realme", "X50 Pro 5G", "phone", 39999,
                                "Camera: Quad camera setup (64 MP + 12 MP + 8 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 865\n" +
                                                "Display: 6.44 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                // Redmi Phones from index 9 to 10
                arr.add(new store("Redmi", "Note 10", "phone", 14999,
                                "Camera: Quad camera setup (48 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: MediaTek Helio G80\n" +
                                                "Display: 6.5 inches, IPS LCD, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Redmi", "9 Pro", "phone", 12499,
                                "Camera: Quad camera setup (64 MP + 8 MP + 5 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 720G\n" +
                                                "Display: 6.4 inches, Super AMOLED, 1080 x 2340 pixels",
                                1));

                // Continue initialization for the remaining Redmi phones

                // Redmi Note 9S
                arr.add(new store("Redmi", "Note 9S", "phone", 15999,
                                "Camera: Quad camera setup (48 MP + 8 MP + 5 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 720G\n" +
                                                "Display: 6.67 inches, IPS LCD, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Redmi", "K30 Pro", "phone", 24999,
                                "Camera: Quad camera setup (64 MP + 13 MP + 5 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 865\n" +
                                                "Display: 6.67 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Redmi", "9A", "phone", 8999,
                                "Camera: Single 13 MP rear camera\n" +
                                                "Processor: MediaTek Helio G25\n" +
                                                "Display: 6.53 inches, IPS LCD, 720 x 1600 pixels",
                                1));

                arr.add(new store("Redmi", "Note 8", "phone", 10999,
                                "Camera: Quad camera setup (48 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 665\n" +
                                                "Display: 6.3 inches, IPS LCD, 1080 x 2340 pixels",
                                1));

                arr.add(new store("Redmi", "K20", "phone", 19999,
                                "Camera: Triple camera setup (48 MP + 13 MP + 8 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 730\n" +
                                                "Display: 6.39 inches, Super AMOLED, 1080 x 2340 pixels",
                                1));

                arr.add(new store("Redmi", "Note 7 Pro", "phone", 13999,
                                "Camera: Dual camera setup (48 MP + 5 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 675\n" +
                                                "Display: 6.3 inches, IPS LCD, 1080 x 2340 pixels",
                                1));

                arr.add(new store("Redmi", "10X", "phone", 18499,
                                "Camera: Quad camera setup (48 MP + 8 MP + 2 MP + 2 MP)\n" +
                                                "Processor: MediaTek Dimensity 820\n" +
                                                "Display: 6.57 inches, IPS LCD, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Redmi", "K40", "phone", 27999,
                                "Camera: Triple camera setup (64 MP + 8 MP + 5 MP)\n" +
                                                "Processor: Qualcomm Snapdragon 870\n" +
                                                "Display: 6.67 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                arr.add(new store("Redmi", "Phone 20", "phone", 11999,
                                "Camera: Dual camera setup (16 MP + 5 MP)\n" +
                                                "Processor: MediaTek Helio G6\n" +
                                                "Display: 6.2 inches, IPS LCD, 720 x 1520 pixels",
                                1));

                // Vivo Spark S9
                // Vivo Spark S9
                arr.add(new store("Vivo", "Spark S9", "phone", 21999,
                                "Camera: Quad - 64 MP (wide), 12 MP (telephoto), 16 MP (ultrawide), 2 MP (macro)\n" +
                                                "Processor: Qualcomm Snapdragon 765\n" +
                                                "Display: 6.8 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                // Vivo Vortex V7
                arr.add(new store("Vivo", "Vortex V7", "phone", 15799,
                                "Camera: Triple - 48 MP (wide), 8 MP (ultrawide), 2 MP (depth)\n" +
                                                "Processor: Vivo Vortex Octa-Core\n" +
                                                "Display: 6.5 inches, IPS LCD, 1080 x 2340 pixels",
                                1));

                // Vivo Pixel P5
                arr.add(new store("Vivo", "Pixel P5", "phone", 9999,
                                "Camera: Dual - 16 MP (wide), 5 MP (depth)\n" +
                                                "Processor: MediaTek Helio P35\n" +
                                                "Display: 6.0 inches, LCD, 720 x 1600 pixels",
                                1));

                // Vivo Nova X
                arr.add(new store("Vivo", "Nova X", "phone", 17999,
                                "Camera: Quad - 50 MP (wide), 8 MP (telephoto), 13 MP (ultrawide), 2 MP (macro)\n" +
                                                "Processor: Exynos 880\n" +
                                                "Display: 6.7 inches, AMOLED, 1080 x 2400 pixels",
                                1));

                // Vivo Spectrum S6
                arr.add(new store("Vivo", "Spectrum S6", "phone", 13299,
                                "Camera: Triple - 20 MP (wide), 8 MP (ultrawide), 2 MP (depth)\n" +
                                                "Processor: Qualcomm Snapdragon 675\n" +
                                                "Display: 6.3 inches, IPS LCD, 1080 x 2340 pixels",
                                1));

                // Under ₹10,000
                // Samsung Galaxy Lite M10
                arr.add(new store("Samsung", "Galaxy Lite M10", "phone", 8999,
                                "Camera: Dual - 13 MP (wide), 5 MP (ultrawide)\n" +
                                                "Processor: Exynos 7870\n" +
                                                "Display: 6.0 inches, TFT, 720 x 1480 pixels",
                                1));

                // Samsung Galaxy J2 Prime
                arr.add(new store("Samsung", "Galaxy J2 Prime", "phone", 6999,
                                "Camera: 8 MP (wide)\n" +
                                                "Processor: MediaTek MT6737T\n" +
                                                "Display: 5.0 inches, PLS TFT, 540 x 960 pixels",
                                1));

                // Under ₹20,000
                // Samsung Galaxy A32
                arr.add(new store("Samsung", "Galaxy A32", "phone", 18999,
                                "Camera: Quad - 64 MP (wide), 8 MP (ultrawide), 5 MP (macro), 5 MP (depth)\n" +
                                                "Processor: MediaTek Helio G80\n" +
                                                "Display: 6.4 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                // Samsung Galaxy M42
                arr.add(new store("Samsung", "Galaxy M42", "phone", 17999,
                                "Camera: Quad - 48 MP (wide), 8 MP (ultrawide), 5 MP (macro), 5 MP (depth)\n" +
                                                "Processor: Qualcomm Snapdragon 750G\n" +
                                                "Display: 6.6 inches, Super AMOLED, 720 x 1600 pixels",
                                1));

                // Under ₹30,000
                // Samsung Galaxy F41
                arr.add(new store("Samsung", "Galaxy F41", "phone", 27999,
                                "Camera: Triple - 64 MP (wide), 8 MP (ultrawide), 5 MP (depth)\n" +
                                                "Processor: Exynos 9611\n" +
                                                "Display: 6.4 inches, Super AMOLED, 1080 x 2340 pixels",
                                1));

                // Samsung Galaxy A52
                arr.add(new store("Samsung", "Galaxy A52", "phone", 29999,
                                "Camera: Quad - 64 MP (wide), 12 MP (ultrawide), 5 MP (macro), 5 MP (depth)\n" +
                                                "Processor: Qualcomm Snapdragon 720G\n" +
                                                "Display: 6.5 inches, Super AMOLED, 1080 x 2400 pixels",
                                1));

                // iPhone SE (2nd generation)
                arr.add(new store("Apple", "iPhone SE (2nd generation)", "phone", 29999,
                                "Camera: Single - 12 MP (wide)\n" +
                                                "Processor: Apple A13 Bionic\n" +
                                                "Display: 4.7 inches, Retina IPS LCD, 750 x 1334 pixels",
                                1));

                // Under ₹50,000
                // iPhone XR
                arr.add(new store("Apple", "iPhone XR", "phone", 49999,
                                "Camera: Single - 12 MP (wide)\n" +
                                                "Processor: Apple A12 Bionic\n" +
                                                "Display: 6.1 inches, Liquid Retina IPS LCD, 828 x 1792 pixels",
                                1));

                // iPhone 11
                arr.add(new store("Apple", "iPhone 11", "phone", 47999,
                                "Camera: Dual - 12 MP (wide), 12 MP (ultrawide)\n" +
                                                "Processor: Apple A13 Bionic\n" +
                                                "Display: 6.1 inches, Liquid Retina IPS LCD, 828 x 1792 pixels",
                                1));

                // Under ₹80,000
                // iPhone 12 Mini
                arr.add(new store("Apple", "iPhone 12 Mini", "phone", 79999,
                                "Camera: Dual - 12 MP (wide), 12 MP (ultrawide)\n" +
                                                "Processor: Apple A14 Bionic\n" +
                                                "Display: 5.4 inches, Super Retina XDR OLED, 1080 x 2340 pixels",
                                1));

                // iPhone 12
                arr.add(new store("Apple", "iPhone 12", "phone", 74999,
                                "Camera: Dual - 12 MP (wide), 12 MP (ultrawide)\n" +
                                                "Processor: Apple A14 Bionic\n" +
                                                "Display: 6.1 inches, Super Retina XDR OLED, 1170 x 2532 pixels",
                                1));

                // Under ₹1,00,000
                // iPhone 12 Pro
                arr.add(new store("Apple", "iPhone 12 Pro", "phone", 99999,
                                "Camera: Triple - 12 MP (wide), 12 MP (ultrawide), 12 MP (telephoto)\n" +
                                                "Processor: Apple A14 Bionic\n" +
                                                "Display: 6.1 inches, Super Retina XDR OLED, 1284 x 2778 pixels",
                                1));

                // iPhone 13
                arr.add(new store("Apple", "iPhone 13", "phone", 89999,
                                "Camera: Dual - 12 MP (wide), 12 MP (ultrawide)\n" +
                                                "Processor: Apple A15 Bionic\n" +
                                                "Display: 6.1 inches, Super Retina XDR OLED, 1170 x 2532 pixels",
                                1));

                // Above ₹1,00,000
                // iPhone 13 Pro
                arr.add(new store("Apple", "iPhone 13 Pro", "phone", 119999,
                                "Camera: Triple - 12 MP (wide), 12 MP (ultrawide), 12 MP (telephoto)\n" +
                                                "Processor: Apple A15 Bionic\n" +
                                                "Display: 6.1 inches, Super Retina XDR ProMotion OLED, 1284 x 2778 pixels",
                                1));

                // iPhone 13 Pro Max
                arr.add(new store("Apple", "iPhone 13 Pro Max", "phone", 129999,
                                "Camera: Triple - 12 MP (wide), 12 MP (ultrawide), 12 MP (telephoto)\n" +
                                                "Processor: Apple A15 Bionic\n" +
                                                "Display: 6.7 inches, Super Retina XDR ProMotion OLED, 1284 x 2778 pixels",
                                1));

                // HP Omen Series
                // Omen 15
                // High-End Laptops
                // HP Spectre Series
                // Spectre x360
                arr.add(new store("HP", "Spectre x360", "laptop", 100000,
                                "Configuration: Intel Core i5/i7, SSD options", 1));

                // HP Omen Series
                // Omen 15
                arr.add(new store("HP", "Omen 15", "laptop", 80000,
                                "Configuration: Intel Core i5/i7, NVIDIA GTX/RTX series, SSD/HDD options", 1));

                // Omen 17
                arr.add(new store("HP", "Omen 17", "laptop", 100000,
                                "Configuration: Intel Core i7/i9, NVIDIA RTX series, SSD/HDD options", 1));

                // Medium Range Laptops
                // HP Pavilion Series
                // Pavilion x360
                arr.add(new store("HP", "Pavilion x360", "laptop", 50000,
                                "Configuration: Intel Core i3/i5/i7, SSD/HDD options", 1));

                // Pavilion Gaming
                arr.add(new store("HP", "Pavilion Gaming", "laptop", 60000,
                                "Configuration: Intel Core i5/i7, NVIDIA GTX series, SSD/HDD options", 1));

                // HP Envy Series
                // Envy x360
                arr.add(new store("HP", "Envy x360", "laptop", 70000,
                                "Configuration: Intel Core i5/i7, AMD Ryzen 5/7, SSD/HDD options", 1));

                // Normal Laptops
                // HP 14 Series
                // HP 14s
                arr.add(new store("HP", "HP 14s", "laptop", 30000,
                                "Configuration: Intel Core i3/i5, SSD/HDD options", 1));

                // HP 14z
                arr.add(new store("HP", "HP 14z", "laptop", 25000,
                                "Configuration: AMD Ryzen 3/5, SSD/HDD options", 1));

                // HP 15 Series
                // HP 15s
                arr.add(new store("HP", "HP 15s", "laptop", 35000,
                                "Configuration: Intel Core i3/i5, SSD/HDD options", 1));

                // HP 15z
                arr.add(new store("HP", "HP 15z", "laptop", 30000,
                                "Configuration: AMD Ryzen 3/5, SSD/HDD options", 1));

                // Lenovo Laptops
                // Lenovo Ideapad 5
                arr.add(new store("Lenovo", "Ideapad 5", "laptop", 55000,
                                "Processor: AMD Ryzen 5 4500U\n" +
                                                "Storage: 512GB SSD",
                                1));

                // Lenovo ThinkPad E14
                arr.add(new store("Lenovo", "ThinkPad E14", "laptop", 50000,
                                "Processor: Intel Core i5-10210U\n" +
                                                "Storage: 1TB HDD",
                                1));

                // Lenovo Legion Y540 (Gaming Laptop)
                arr.add(new store("Lenovo", "Legion Y540", "laptop", 90000,
                                "Processor: Intel Core i7-9750H\n" +
                                                "Storage: 256GB SSD + 1TB HDD",
                                1));

                // Lenovo Yoga C940
                arr.add(new store("Lenovo", "Yoga C940", "laptop", 120000,
                                "Processor: Intel Core i7-1065G7\n" +
                                                "Storage: 512GB SSD",
                                1));

                // Lenovo Ideapad Flex 5
                arr.add(new store("Lenovo", "Ideapad Flex 5", "laptop", 65000,
                                "Processor: AMD Ryzen 7 4700U\n" +
                                                "Storage: 256GB SSD",
                                1));

                // Chargers
                // Mi 18W Fast Charger
                arr.add(new store("Mi", " 18W Fast Charger", "charger", 499,
                                " 18W power output, fast charging support, and compact design. ", 1));

                // Anker PowerPort Speed PD 30
                arr.add(new store("Anker", " PowerPort Speed PD 30", "charger", 1199,
                                "30W power output, Power Delivery support, and foldable plug design.", 1));

                // Samsung 25W Super Fast Charger
                arr.add(new store("Samsung", " 25W Super Fast Charger", "charger", 1499,
                                "25W power output, USB Type-C to Type-C cable included, and adaptive fast charging.",
                                1));

                // Apple 20W USB-C Power Adapter
                arr.add(new store("Apple", " 20W USB-C Power Adapter", "charger", 1799,
                                "20W power output, USB-C port, and compatible with various Apple devices.", 1));

                // Aukey Omnia Mix3 90W
                arr.add(new store("Aukey Omnia", " Mix3 90W", "charger", 4999,
                                "90W power output, GaN technology for a compact design", 1));

                // True Wireless Earbuds
                // Sony WF-1000XM4
                // Earbuds
                // Sony WF-1000XM4
                arr.add(new store("Sony", "Sony WF-1000XM4", "earbuds", 12000,
                                "True wireless earbuds with industry-leading noise cancellation, long battery life, and exceptional audio quality.",
                                1));

                // Apple AirPods Pro
                arr.add(new store("Apple", "AirPods Pro", "earbuds", 15000,
                                "Active noise cancellation, customizable fit, and a compact design. Seamless integration with Apple devices.",
                                1));

                // JBL Free X
                arr.add(new store("JBL", "Free X", "earbuds", 4000,
                                "Wireless in-ear headphones with deep bass, sweat-proof design, and intuitive controls.",
                                1));

                // Sennheiser Momentum True Wireless 2
                arr.add(new store("Sennheiser", "Momentum True Wireless 2", "earbuds", 18000,
                                "Premium true wireless earbuds with superior sound quality, touch controls, and customizable equalizer settings.",
                                1));

                // Bose QuietComfort Earbuds
                arr.add(new store("Bose", "QuietComfort Earbuds", "earbuds", 16000,
                                "True wireless earbuds with active noise cancellation, comfortable fit, and clear, balanced sound.",
                                1));

                // Samsung Galaxy Buds Pro
                arr.add(new store("Samsung", "Galaxy Buds Pro", "earbuds", 9000,
                                "True wireless earbuds with intelligent active noise cancellation, water resistance, and customizable touch controls.",
                                1));

                // Anker Soundcore Liberty Air 2 Pro
                arr.add(new store("Anker", "Soundcore Liberty Air 2 Pro", "earbuds", 7000,
                                "Affordable true wireless earbuds with customizable sound profiles, wireless charging, and a secure fit.",
                                1));

                // OnePlus Buds Pro
                arr.add(new store("OnePlus", "Buds Pro", "earbuds", 10000,
                                "True wireless earbuds with adaptive noise cancellation, immersive sound, and a sleek design.",
                                1));

                // Jabra Elite 75t
                arr.add(new store("Jabra", "Elite 75t", "earbuds", 8500,
                                "Compact true wireless earbuds with a secure fit, customizable equalizer, and strong battery life.",
                                1));

                // RHA TrueConnect 2
                arr.add(new store("RHA", "TrueConnect 2", "earbuds", 6000,
                                "True wireless earbuds with a durable design, immersive sound, and an extended battery life.",
                                1));

                // Wired Earphones
                // Boat BassHeads 100
                // Earphones
                // Boat BassHeads 100
                arr.add(new store("Boat", "BassHeads 100", "earphone", 499,
                                "In-ear wired earphones with deep bass, lightweight design, and a tangle-free cable.",
                                1));

                // Realme Buds Classic
                arr.add(new store("Realme", "Buds Classic", "earphone", 699,
                                "Wired earphones with HD sound quality, ergonomic design, and inline microphone for hands-free calling.",
                                1));

                // JBL C100SI
                arr.add(new store("JBL", "C100SI", "earphone", 799,
                                "In-ear wired earphones with powerful bass, a one-button universal remote, and noise isolation.",
                                1));

                // Mi Basic Wired Earphones
                arr.add(new store("Mi", "Basic Wired Earphones", "earphone", 299,
                                "Budget-friendly wired earphones with HD sound quality, comfortable fit, and a built-in microphone.",
                                1));

                // boAt BassHeads 152
                arr.add(new store("Boat", "BassHeads 152", "earphone", 899,
                                "In-ear wired earphones with immersive sound, metallic finish, and an in-line microphone.",
                                1));

                // Philips SHE1505
                arr.add(new store("Philips", "SHE1505", "earphone", 549,
                                "Wired earphones with deep bass, comfortable ear caps, and a durable design.", 1));

                // Skullcandy Ink'd Plus
                arr.add(new store("Skullcandy", "Ink'd Plus", "earphone", 799,
                                "In-ear wired earphones with noise isolation, enhanced bass, and a flat cable for tangle-free use.",
                                1));

                // Ant Audio W56
                arr.add(new store("Ant Audio", "W56", "earphone", 699,
                                "Wired earphones with clear sound, noise isolation, and an ergonomic design for a comfortable fit.",
                                1));

                // Leaf Bass Wired Earphones
                arr.add(new store("Leaf", "Bass Wired Earphones", "earphone", 599,
                                "In-ear wired earphones with deep bass, metal build, and noise isolation.", 1));

                // Portronics Conch Delta
                arr.add(new store("Portronics", "Conch Delta", "earphone", 499,
                                "Budget-friendly wired earphones with crystal-clear sound, a comfortable fit", 1));

                // Wireless On-Ear Headphones
                // boAt Rockerz 400
                // Headphones
                // boAt Rockerz 400
                arr.add(new store("boAt", "Rockerz 400", "headphone", 1499,
                                "Wireless on-ear headphones with deep bass, up to 8 hours of playback, and foldable design.",
                                1));

                // Sony WH-CH510
                arr.add(new store("Sony", "WH-CH510", "headphone", 3999,
                                "Wireless on-ear headphones with clear sound, up to 35 hours of battery life, and swivel design for easy portability.",
                                1));

                // Over-Ear Wireless Headphones
                // Sennheiser HD 450BT
                arr.add(new store("Sennheiser", "HD 450BT", "headphone", 12999,
                                "Over-ear wireless headphones with active noise cancellation, deep bass, and touch controls.",
                                1));

                // JBL Live 650BTNC
                arr.add(new store("JBL", "Live 650BTNC", "headphone", 9999,
                                "Over-ear wireless headphones with noise cancellation, immersive sound, and voice assistants support.",
                                1));

                // Audio-Technica ATH-M50x
                arr.add(new store("Audio-Technica", "ATH-M50x", "headphone", 14999,
                                "Professional studio monitor headphones with exceptional clarity, sound isolation, and detachable cables.",
                                1));

                // Bose QuietComfort 35 II
                arr.add(new store("Bose", "QuietComfort 35 II", "headphone", 25000,
                                "Over-ear wireless headphones with world-class noise cancellation, balanced sound, and comfortable fit.",
                                1));

                // Beyerdynamic DT 990 Pro
                arr.add(new store("Beyerdynamic", "DT 990 Pro", "headphone", 16999,
                                "Open-back over-ear studio headphones with detailed sound reproduction and comfortable velour earpads.",
                                1));

                // Skullcandy Crusher ANC
                arr.add(new store("Skullcandy", "Crusher ANC", "headphone", 18999,
                                "Over-ear wireless headphones with adjustable sensory bass, active noise cancellation, and personalized sound.",
                                1));

                // Premium Over-Ear Wireless Headphones
                // Bang & Olufsen Beoplay H9
                arr.add(new store("Bang & Olufsen", "Beoplay H9", "headphone", 40000,
                                "Premium over-ear wireless headphones with luxurious materials, advanced active noise cancellation, and touch controls.",
                                1));

                // Audeze LCD-2
                arr.add(new store("Audeze", "LCD-2", "headphone", 49999,
                                "High-end planar magnetic over-ear headphones with rich, detailed sound and premium build quality.",
                                1));

                // Wireless In-Ear Headphones
                // // Bluetooth Earphones
                // boAt Rockerz 255
                arr.add(new store("bluetooth", "boAt", "Rockerz 255", 999,
                                "Wireless in-ear headphones with deep bass, up to 6 hours of playback, and IPX5 water resistance.",
                                1));

                // Mi Neckband Bluetooth Earphones Pro
                arr.add(new store("Mi", "Neckband Bluetooth Earphones Pro", "bluetooth", 1499,
                                "Neckband-style wireless earphones with active noise cancellation, up to 20 hours of battery life, and magnetic earbuds.",
                                1));

                // JBL Free X
                arr.add(new store("JBL", "Free X", "bluetooth", 3499,
                                "True wireless earbuds with JBL Signature Sound, up to 4 hours of playback, and hands-free calling.",
                                1));

                // Truly Wireless Earbuds
                // Sony WF-XB700
                // Earbuds
                // Sony WF-XB700
                arr.add(new store("earbuds", "WF-XB700", "Sony", 7999,
                                "Truly wireless earbuds with Extra Bass, up to 9 hours of battery life, and water-resistant design.",
                                1));

                // Bluetooth Earphones
                // OnePlus Bullets Wireless Z
                arr.add(new store("OnePlus", "Bullets Wireless Z", "earbuds", 2199,
                                "Neckband-style wireless earphones with Warp Charge, 20 hours of battery life, and quick switch between devices.",
                                1));

                // Sennheiser CX 400BT
                arr.add(new store("Sennheiser", "CX 400BT", "earbuds", 14999,
                                "True wireless earbuds with customizable touch controls, Sennheiser's signature sound, and 7-hour battery life.",
                                1));

                // Apple AirPods Pro
                arr.add(new store("Apple", "AirPods Pro", "earbuds", 24999,
                                "True wireless earbuds with active noise cancellation, transparency mode, and adaptive EQ for immersive sound.",
                                1));

                // Bose Sport Earbuds
                arr.add(new store("Bose", "Sport Earbuds", "earbuds", 18000,
                                "True wireless sport earbuds with secure fit, water-resistant design, and up to 5 hours of battery life.",
                                1));

                // True Wireless Sports Earbuds
                // Bang & Olufsen Beoplay E8 Sport
                arr.add(new store("Bang & Olufsen", "Beoplay E8 Sport", "earbuds", 29999,
                                "True wireless sports earbuds with powerful sound, customizable fit, and water-resistant construction.",
                                1));

                // Jabra Elite 85t
                arr.add(new store("Jabra", "Elite 85t", "earbuds", 19999,
                                "True wireless earbuds with advanced noise cancellation, customizable sound profiles, and up to 25 hours of battery life.",
                                1));

                // Power Banks
                // Mi Power Bank 3i
                // Power Banks
                // Mi Power Bank 3i
                arr.add(new store("Mi", "Power Bank 3i", "powerbank", 999,
                                "10,000mAh capacity, dual USB output, and supports 18W fast charging.", 1));

                // Anker PowerCore Essential 20000
                arr.add(new store("Anker", "PowerCore Essential 20000", "powerbank", 2499,
                                "20,000mAh capacity, PowerIQ technology for fast charging, and dual USB-A ports.", 1));

                // Ambrane PP-20 Pro
                arr.add(new store("Ambrane", "PP-20 Pro", "powerbank", 799,
                                "15,000mAh capacity, dual USB output, and compact design.", 1));

                // Samsung Wireless Power Bank
                arr.add(new store("Samsung", "Wireless Power Bank", "powerbank", 3999,
                                "10,000mAh capacity, wireless charging capability, and USB-C input/output.", 1));

                // Aukey PB-Y23
                arr.add(new store("Aukey", "PB-Y23", "powerbank", 1799,
                                "20,000mAh capacity, Quick Charge 3.0 technology, and dual USB-A and USB-C ports.", 1));

                // Apple MagSafe Battery Pack
                arr.add(new store("Apple", "MagSafe Battery Pack", "powerbank", 7999,
                                "MagSafe compatible, designed for iPhone 12 series, and 1,460mAh capacity.", 1));

                // Sony CP-V10B
                arr.add(new store("Sony", "CP-V10B", "powerbank", 2499,
                                "10,000mAh capacity, slim and compact design, and dual USB output.", 1));

                // OnePlus Power Bank
                arr.add(new store("OnePlus", "Power Bank", "powerbank", 1299,
                                "10,000mAh capacity, fast charging support, and dual USB-A ports.", 1));

                // Ravpower PD Pioneer 20000mAh
                arr.add(new store("Ravpower", "PD Pioneer 20000mAh", "powerbank", 4999,
                                "20,000mAh capacity, Power Delivery support, and multiple charging ports.", 1));

                // Mophie Powerstation XXL
                arr.add(new store("Mophie", "Powerstation XXL", "powerbank", 9999,
                                "20,000mAh capacity, USB-C PD input/output, and Priority+ charging feature.", 1));

                System.out.println(
                                "===============================================================================================");
                System.out.println();
                System.out.println("                  WELCOME TO THE ELECTRONIC SHOP MANAGEMENT SYSTEM ");
                System.out.println();
                System.out.println(
                                "===============================================================================================");
                System.out.println();
                System.out.println("        1. CUSTOMER       2. PRODUCT MANAGER      ");

                System.out.print("ENTER YOUR CHOICE : ");
                int n = sc.nextInt();
                System.out.println();
                if (n == 1) {
                        pManagement pt = new pManagement();

                        while (true) {
                                boolean b = false;

                                System.out.println();
                                System.out.println();
                                System.out.println("           CHECKOUT OUR PRODUCTS         ");
                                System.out.println();
                                System.out.println("  PRESS 1 FOR LAPTOPS  ");
                                System.out.println("  PRESS 2 FOR PHONES  ");
                                System.out.println("  PRESS 3 FOR EARPHONES  ");
                                System.out.println("  PRESS 4 FOR HEADPHONES  ");
                                System.out.println("  PRESS 5 FOR BLUETOOTH  ");
                                System.out.println("  PRESS 6 FOR CHARGERS  ");
                                System.out.println("  PRESS 7 FOR EARBUDS  ");
                                System.out.println("  PRESS 8 FOR EXIT  ");
                                System.out.println("  PRESS 9 FOR MANAGE PRODUCTS  ");

                                pManagement pm2 = new pManagement();

                                System.out.print("ENTER YOUR CHOICE : ");
                                int k = sc.nextInt();
                                switch (k) {
                                        case 1:
                                                System.out.println("  1. LAPTOPS BY PRICE RANGE  ");
                                                System.out.println("  2. ALL LAPTOPS THAN PRESS  ");
                                                System.out.println("  3. LAPTOPS  BY DESCRIPTION  ");
                                                System.out.println("  4. LAPTOPS  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");
                                                int bh = 0;
                                                System.out.println();
                                                System.out.print("ENTER YOUR CHOICE : ");
                                                int p = sc.nextInt();
                                                if (p == 1) {

                                                        arr.get(0).searchByRange(arr, "laptop");
                                                } else if (p == 2) {
                                                        for (int i = 0; i < arr.size()
                                                                        && arr.get(i).type != null; i++) {
                                                                if ("laptop".equalsIgnoreCase(arr.get(i).type)) {
                                                                        arr.get(i).getData();
                                                                        for (int j = 1; j <= 100; j++) {
                                                                                System.out.print("_");
                                                                        }
                                                                        System.out.println();
                                                                        for (int j = 1; j <= 100; j++) {
                                                                                System.out.print("_");
                                                                        }
                                                                        System.out.println();
                                                                }
                                                        }
                                                } else if (p == 3) {

                                                        arr.get(0).searchByDec(arr, "laptop");

                                                } else if (p == 4) {

                                                        arr.get(0).searchByName(arr, "laptop");

                                                } else if (p == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p == 6) {

                                                }
                                                break;

                                        case 2:

                                                System.out.println("  1. PHONES BY PRICE RANGE  ");
                                                System.out.println("  2. ALL PHONES THAN PRESS  ");
                                                System.out.println("  3. PHONES  BY DESCRIPTION  ");
                                                System.out.println("  4. PHONES  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");

                                                System.out.print("ENTER YOUR CHOICE : ");
                                                int p2 = sc.nextInt();
                                                System.out.println();

                                                if (p2 == 1) {

                                                        arr.get(0).searchByRange(arr, "phone");

                                                } else if (p2 == 2) {

                                                        arr.get(0).print(arr, "phone");

                                                } else if (p2 == 3) {

                                                        arr.get(0).searchByDec(arr, "phone");

                                                } else if (p2 == 4) {

                                                        arr.get(0).searchByName(arr, "phone");

                                                } else if (p2 == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p2 == 6) {

                                                }
                                                break;

                                        case 3:
                                                System.out.println("  1. EARPHONES BY PRICE RANGE  ");
                                                System.out.println("  2. ALL EARPHONES THAN PRESS  ");
                                                System.out.println("  3. EARPHONES  BY DESCRIPTION  ");
                                                System.out.println("  4. EARPHONES  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");

                                                System.out.print("ENTER YOUR CHOICE : ");
                                                int p3 = sc.nextInt();
                                                System.out.println();

                                                if (p3 == 1) {

                                                        arr.get(0).searchByRange(arr, "earphone");

                                                } else if (p3 == 2) {

                                                        arr.get(0).print(arr, "earphone");

                                                } else if (p3 == 3) {

                                                        arr.get(0).searchByDec(arr, "earphone");

                                                } else if (p3 == 4) {

                                                        arr.get(0).searchByName(arr, "earphone");

                                                } else if (p3 == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p3 == 6) {

                                                }
                                                break;

                                        case 4:
                                                System.out.println("  1. HEADPHONES BY PRICE RANGE  ");
                                                System.out.println("  2. ALL HEADPHONES THAN PRESS  ");
                                                System.out.println("  3. HEADPHONES  BY DESCRIPTION  ");
                                                System.out.println("  4. HEADPHONES  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");

                                                System.out.print("ENTER YOUR CHOICE : ");
                                                int p4 = sc.nextInt();
                                                System.out.println();

                                                if (p4 == 1) {

                                                        arr.get(0).searchByRange(arr, "headphone");

                                                } else if (p4 == 2) {

                                                        arr.get(0).print(arr, "headphone");

                                                } else if (p4 == 3) {

                                                        arr.get(0).searchByDec(arr, "headphone");

                                                } else if (p4 == 4) {

                                                        arr.get(0).searchByName(arr, "headphone");

                                                } else if (p4 == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p4 == 6) {

                                                }
                                                break;

                                        case 5:
                                                System.out.println("  1. BLUETOOTH BY PRICE RANGE  ");
                                                System.out.println("  2. ALL BLUETOOTH THAN PRESS  ");
                                                System.out.println("  3. BLUETOOTH  BY DESCRIPTION  ");
                                                System.out.println("  4. LAPTOPS  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");

                                                System.out.print("ENTER YOUR CHOICE : ");

                                                int p5 = sc.nextInt();
                                                System.out.println();

                                                if (p5 == 1) {

                                                        arr.get(0).searchByRange(arr, "bluetooth");

                                                } else if (p5 == 2) {

                                                        arr.get(0).print(arr, "bluetooth");

                                                } else if (p5 == 3) {

                                                        arr.get(0).searchByDec(arr, "bluetooth");

                                                } else if (p5 == 4) {

                                                        arr.get(0).searchByName(arr, "bluetooth");

                                                } else if (p5 == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p5 == 6) {

                                                }
                                                break;

                                        case 6:
                                                System.out.println("  1. CHARGERS BY PRICE RANGE  ");
                                                System.out.println("  2. ALL CHARGERS THAN PRESS  ");
                                                System.out.println("  3. CHARGERS  BY DESCRIPTION  ");
                                                System.out.println("  4. CHARGERS  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");

                                                System.out.print("ENTER YOUR CHOICE : ");
                                                int p6 = sc.nextInt();
                                                System.out.println();

                                                if (p6 == 1) {

                                                        arr.get(0).searchByRange(arr, "charger");

                                                } else if (p6 == 2) {

                                                        arr.get(0).print(arr, "charger");

                                                } else if (p6 == 3) {

                                                        arr.get(0).searchByDec(arr, "charger");

                                                } else if (p6 == 4) {

                                                        arr.get(0).searchByName(arr, "charger");

                                                } else if (p6 == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p6 == 6) {

                                                }
                                                break;
                                        case 7:

                                                System.out.println("  1. EARBUDS BY PRICE RANGE  ");
                                                System.out.println("  2. ALL EARBUDS THAN PRESS  ");
                                                System.out.println("  3. EARBUDS  BY DESCRIPTION  ");
                                                System.out.println("  4. EARBUDS  BY NAME ");
                                                System.out.println("  5. YOU WANT TO BUY ");
                                                System.out.println("  6. BACK TO THE HOME PAGE");

                                                System.out.print("ENTER YOUR CHOICE : ");
                                                int p7 = sc.nextInt();
                                                System.out.println();

                                                if (p7 == 1) {

                                                        arr.get(0).searchByRange(arr, "earbuds");

                                                } else if (p7 == 2) {

                                                        arr.get(0).print(arr, "earbuds");

                                                } else if (p7 == 3) {

                                                        arr.get(0).searchByDec(arr, "earbuds");

                                                } else if (p7 == 4) {

                                                        arr.get(0).searchByName(arr, "earbuds");

                                                } else if (p7 == 5) {

                                                        pm2.generateBill(arr);

                                                } else if (p7 == 6) {

                                                }
                                                break;

                                        case 8:
                                                b = true;
                                                break;

                                        case 9:
                                                int choice;
                                                pManagement ps = new pManagement();
                                                ps.loginmanager();

                                                do {
                                                        System.out.println("1. Add Product In Invenory");
                                                        System.out.println("2. Remove Product In Inventory");
                                                        System.out.println("3. Search by Brand name");
                                                        System.out.println("4. Search by Model name");
                                                        System.out.println("5. Search by Specifications");
                                                        System.out.println("6. search By Price");
                                                        System.out.println("7. Update Item Quantity");
                                                        System.out.println("8. Display Inventory");
                                                        System.out.println("9. bill");
                                                        System.out.println("10. Exit");
                                                        System.out.print("Enter your choice: ");
                                                        choice = sc.nextInt();
                                                        pManagement pm = new pManagement();
                                                        switch (choice) {
                                                                case 1:
                                                                        pm.addProduct(arr);
                                                                        break;

                                                                case 2:
                                                                        pm.removeItem(arr);
                                                                        break;
                                                                case 3:

                                                                        System.out.print("  ENTER  PRODUCT : ");
                                                                        sc.nextLine();
                                                                        String typ7 = sc.nextLine().toLowerCase();
                                                                        System.out.println();

                                                                        for (int i = 0; i < arr.size(); i++) {

                                                                                if (arr.get(i).type.equalsIgnoreCase(
                                                                                                typ7)) {

                                                                                        arr.get(i).getData();
                                                                                        for (int l = 0; l <= 100; l++) {
                                                                                                System.out.print("_");
                                                                                        }
                                                                                        System.out.println();
                                                                                        for (int l = 0; l <= 100; l++) {
                                                                                                System.out.print("_");
                                                                                        }

                                                                                }

                                                                        }

                                                                        break;
                                                                case 4:

                                                                        System.out.print("  ENTER  PRODUCT : ");
                                                                        String typ10 = sc.next().toLowerCase();
                                                                        System.out.println();
                                                                        arr.get(0).searchByName(arr, typ10);

                                                                        break;
                                                                case 5:
                                                                        String mt = sc.nextLine().toLowerCase();
                                                                        arr.get(0).searchByDec(arr, mt);

                                                                        break;
                                                                case 6:
                                                                        System.out.println(" Enter type of product : ");
                                                                        String at1 = sc.next().toLowerCase();
                                                                        arr.get(0).searchByRange(arr, at1);
                                                                        break;
                                                                case 7:
                                                                        pm.updateItemQuantity(arr);
                                                                        break;
                                                                case 8:
                                                                        System.out.println(" Enter type of product : ");
                                                                        String at = sc.next().toLowerCase();
                                                                        store.print(arr, at);
                                                                        break;
                                                                case 9:
                                                                        pm.generateBill(arr);
                                                                        break;
                                                                case 10:
                                                                        System.out.println(
                                                                                        "==== Thank You , Visite Again ====");
                                                                        break;
                                                                default:
                                                                        System.out.println(
                                                                                        "Invalid choice. Please Enter No between 1 to 10.");
                                                        }

                                                } while (choice != 10);

                                }// switch

                                if (b == true) {
                                        break;
                                }
                        } // while

                } else {

                }
                if (n == 2) {

                        int choice;
                        pManagement ps = new pManagement();
                        ps.loginmanager();
                        do {
                                System.out.println("1. Add Product In Invenory");
                                System.out.println("2. Remove Product In Inventory");
                                System.out.println("3. Search by Product name");
                                System.out.println("4. Search by Model name");
                                System.out.println("5. Search by Specifications");
                                System.out.println("6. search By Price");
                                System.out.println("7. Update Item Quantity");
                                System.out.println("8. Display Inventory");
                                System.out.println("9. bill");
                                System.out.println("10. Exit");
                                System.out.print("Enter your choice: ");
                                choice = sc.nextInt();
                                pManagement pm = new pManagement();
                                switch (choice) {
                                        case 1:
                                                pm.addProduct(arr);

                                                break;

                                        case 2:
                                                pm.removeItem(arr);
                                                break;
                                        case 3:

                                                System.out.print("  ENTER  PRODUCT NAME: ");
                                                sc.nextLine();
                                                String typ7 = sc.nextLine().toLowerCase();
                                                System.out.println();

                                                for (int i = 0; i < arr.size(); i++) {

                                                        if (arr.get(i).type.equalsIgnoreCase(typ7)) {

                                                                arr.get(i).getData();
                                                                for (int l = 0; l <= 100; l++) {
                                                                        System.out.print("_");
                                                                }
                                                                System.out.println();
                                                                for (int l = 0; l <= 100; l++) {
                                                                        System.out.print("_");
                                                                }

                                                        }

                                                }

                                                break;
                                        case 4:

                                                System.out.print("  ENTER  PRODUCT : ");
                                                String typ10 = sc.next().toLowerCase();
                                                System.out.println();
                                                arr.get(0).searchByName(arr, typ10);
                                                break;
                                        case 5:
                                                String mt = sc.nextLine().toLowerCase();
                                                arr.get(0).searchByDec(arr, mt);

                                                break;
                                        case 6:
                                                System.out.println(" Enter type of product : ");
                                                String at1 = sc.next().toLowerCase();
                                                arr.get(0).searchByRange(arr, at1);
                                                break;
                                        case 7:
                                                pm.updateItemQuantity(arr);
                                                break;
                                        case 8:
                                                System.out.println(" Enter type of product : ");
                                                String at = sc.next().toLowerCase();
                                                store.print(arr, at);
                                                break;
                                        case 9:
                                                pm.generateBill(arr);
                                                break;
                                        case 10:
                                                System.out.println("==== Thank You , Visite Again ====");
                                                break;
                                        default:
                                                System.out.println("Invalid choice. Please Enter No between 1 to 10.");
                                }

                        } while (choice != 10);

                } // main if else
                System.out.println();

        }

}

class pManagement {

        private static boolean isLoggedIn = false;

        Scanner scanner = new Scanner(System.in);

        private static final String USERNAME = "ishan";
        private static final String PASSWORD = "2006";

        void loginmanager() {

                int attempts = 0;

                do {
                        System.out.println();
                        System.out.println();
                        System.out.print("Enter username: ");
                        String username = scanner.next();

                        System.out.print("Enter password: ");
                        String password = scanner.next();

                        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                                System.out.println("Login successful!");
                                isLoggedIn = true;
                                break;
                        } else {
                                System.out.println("Invalid username or password. Please try again.");
                                attempts++;
                        }
                } while (attempts < 3);

                if (!isLoggedIn) {
                        System.out.println("Too many login attempts. Exiting the program.");
                        System.exit(0);
                }
        }

        void addProduct(ArrayList<store> arr) {

                System.out.print("Enter Product : ");
                String type1 = scanner.next().toLowerCase();

                System.out.print("Enter brand: ");
                String brandName1 = scanner.next().toLowerCase();

                scanner.nextLine();
                System.out.print("Enter model: ");
                String varName1 = scanner.nextLine().toLowerCase();

                System.out.print("Enter price: ");
                int cost1 = scanner.nextInt();

                System.out.print("Enter quantity: ");
                int quantity1 = scanner.nextInt();

                scanner.nextLine();
                System.out.print("Enter specifications: ");
                String detail1 = scanner.nextLine().toLowerCase();

                arr.add(new store(brandName1, varName1, type1, cost1, detail1, quantity1));

                System.out.println("Product added successfully!");

        }

        void removeItem(ArrayList<store> arr) {
                System.out.print("Enter brand to remove item: ");
                String removeBrand = scanner.next();

                scanner.nextLine(); // consume the newline character
                System.out.print("Enter model to remove item: ");
                String removeModel = scanner.nextLine();

                boolean found = false;

                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        if (arr.get(i).brandName.equalsIgnoreCase(removeBrand)
                                        && arr.get(i).varName.equalsIgnoreCase(removeModel)) {
                                // Remove the item at index i
                                arr.remove(i);

                                System.out.println("Item removed successfully for " + removeBrand + " " + removeModel);
                                found = true;
                                break;
                        }
                }

                if (!found) {
                        System.out.println("Product not found for removal.");
                }
        }

        void updateItemQuantity(ArrayList<store> arr) {

                System.out.print("Enter brand to update quantity: ");
                String updateBrand = scanner.next();

                scanner.nextLine();
                System.out.print("Enter model to update quantity: ");
                String updateModel = scanner.nextLine();

                boolean found = false;

                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        if (arr.get(i).brandName.equalsIgnoreCase(updateBrand)
                                        && arr.get(i).varName.equalsIgnoreCase(updateModel)) {
                                System.out.print("Enter new quantity: ");
                                int newQuantity = scanner.nextInt();

                                // Update the quantity
                                arr.get(i).quantity += newQuantity;

                                System.out.println("Quantity updated successfully.");
                                System.out.println("Updated Quantity for " + updateBrand + " " + updateModel + ": "
                                                + arr.get(i).quantity);

                                found = true;
                                break;
                        }
                }

                if (!found) {
                        System.out.println("Product not found for quantity update.");
                }
        }

        void generateBill(ArrayList<store> arr) {
                System.out.print("Enter brand for billing: ");
                String billingBrand = scanner.nextLine();

                scanner.nextLine();
                System.out.print("Enter model for billing: ");
                String billingModel = scanner.nextLine();

                boolean found = false;

                for (int i = 0; i < arr.size() && arr.get(i).type != null; i++) {
                        if (arr.get(i).brandName.equalsIgnoreCase(billingBrand)
                                        && arr.get(i).varName.equalsIgnoreCase(billingModel)) {
                                System.out.print("Enter quantity to purchase: ");
                                int quantityToPurchase = scanner.nextInt();

                                if (quantityToPurchase > 0 && quantityToPurchase <= arr.get(i).quantity) {
                                        int totalCost = (arr.get(i).cost * quantityToPurchase)
                                                        - (arr.get(i).cost * quantityToPurchase) / 10;

                                        // Print the bill header

                                        System.out.println(
                                                        "================================================================================================================================================");
                                        System.out.println(
                                                        "||                                                      Your Bill                                                                             ||");
                                        System.out.println(
                                                        "================================================================================================================================================");
                                        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Brand", "Model", "Price",
                                                        "Quantity", "Total Cost after discount");
                                        System.out.println(
                                                        "================================================================================================================================================");

                                        // Print the purchased item details
                                        System.out.printf("%-20s%-20s%-20d%-20d%-20d\n", arr.get(i).brandName,
                                                        arr.get(i).varName,
                                                        arr.get(i).cost, quantityToPurchase, totalCost);

                                        // Update inventory after purchase
                                        arr.get(i).quantity -= quantityToPurchase;

                                        // Print the total cost and closing remarks
                                        System.out.println(
                                                        "================================================================================================================================================");
                                        System.out.printf("%-80s%d\n", "Total amount:", totalCost);
                                        System.out.println(
                                                        "================================================================================================================================================");
                                        System.out.println(
                                                        "||                                                thank you for your purchase                                                                 ||");
                                        System.out.println(
                                                        "================================================================================================================================================");

                                        found = true;
                                } else {
                                        System.out.println("Invalid quantity. Please enter a valid quantity.");
                                }
                        }
                }

                if (!found) {
                        System.out.println("Product not found for billing.");
                }
        }

}
