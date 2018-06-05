import java.util.Arrays;
import java.util.Scanner;

public class ImageAvg{
	public static void main(String[] args) {
		System.out.println("Enter the limit :");
		Scanner s = new Scanner(System.in);
		
		int count = s.nextInt();
        s.nextLine();
        if(count<=0){
        	System.out.println("limit should be greater than 0. Please try again");
        	return;
        }
        
        System.out.println("Enter values of Image 1:");
        int[] image1 = readNumbers(count);

        System.out.println("Enter values of Image 2:");
        int[] image2 = readNumbers(count);

        int[] imgAvg = findAvg(image1,image2,count);
        System.out.println("Image Average: ");
        for(int i=0;i<count;i++){
        	System.out.print(imgAvg[i]+" ");
        }
        System.out.println();

        int[] normalisedImg = normalise(imgAvg,count);
        System.out.println("Normalised Image: ");

        for(int i=0;i<count;i++){
        	System.out.print(normalisedImg[i]+" ");
        }
	}

	private static int[] readNumbers(int count){
		int [] numbers = new int[count];
        Scanner numScanner = new Scanner(System.in);
        for (int i = 0; i < count; i++) {
            numbers[i] = numScanner.nextInt();
            if(numbers[i]<0 || numbers[i]>255){
            	System.out.println("Invalid input! Please try again.");
            	System.exit(0);
            }
        }
        return numbers;
	}

	private static int[] findAvg(int[] img1,int[] img2,int count){
		int[] imgAvg = new int[count];
		for(int i=0;i<count;i++){
			imgAvg[i]=(img1[i]+img2[i])/2;
		}
		return imgAvg;
	}

	private static int[] normalise(int[] img,int count){
		int largest = 0;
		//finding largest
		for(int i=0;i<count;i++){
			if(img[i]>largest){
				largest = img[i];
			}
		}
		float normFact = (float)255/largest;

		System.out.println("normFact "+normFact);

		//finding normalised values
		int[] imgNorm = new int[count];
		for(int i=0;i<count;i++){
			imgNorm[i]=(int)(img[i]*normFact);
		}
		return imgNorm;
	}
}