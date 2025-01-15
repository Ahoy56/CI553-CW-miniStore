package catalogue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BetterBasketTest {

	@Test
	void testAddProduct() {
		ArrayList<Product> br = new ArrayList<>();
		Product p1 = new Product("0001","toaster",12.3, 1);
		Product p2 = new Product("0002","Kettle",12.7, 1);
		br.add(p1);
		br.add(p2);
		br.add(p2);
		br.add(p2);
		assertEquals(2, br.size(), "incorrect size after merge");
		assertEquals(3, br.get(1).getQuantity(),"incorrect quantity after merge");
	}

}
