package br.com.tiagods.model;

public class NegocioMotivoPerdaTrio<T,U,V>{
		private T value1;
		private U value2;
		private V value3;
		
		public NegocioMotivoPerdaTrio(T value1, U value2, V value3) {
			this.value1=value1;
			this.value2=value2;
			this.value3=value3;
		}

		/**
		 * @return the value1
		 */
		public T getValue1() {
			return value1;
		}

		/**
		 * @param value1 the value1 to set
		 */
		public void setValue1(T value1) {
			this.value1 = value1;
		}

		/**
		 * @return the value2
		 */
		public U getValue2() {
			return value2;
		}

		/**
		 * @param value2 the value2 to set
		 */
		public void setValue2(U value2) {
			this.value2 = value2;
		}

		/**
		 * @return the value3
		 */
		public V getValue3() {
			return value3;
		}

		/**
		 * @param value3 the value3 to set
		 */
		public void setValue3(V value3) {
			this.value3 = value3;
		}
		
	}
