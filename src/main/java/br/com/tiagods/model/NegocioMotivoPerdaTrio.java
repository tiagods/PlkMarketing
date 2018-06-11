package br.com.tiagods.model;

public class NegocioMotivoPerdaTrio<MotivoPerda,Calendar,String>{
		private MotivoPerda motivo;
		private Calendar dataPerda;
		private String detalhes;
		
		public NegocioMotivoPerdaTrio(MotivoPerda motivo, Calendar dataPerda, String detalhes) {
			this.motivo=motivo;
			this.dataPerda=dataPerda;
			this.detalhes=detalhes;
		}
		public Calendar getDataPerda() {
			return dataPerda;
		}
		public String getDetalhes() {
			return detalhes;
		}
		public MotivoPerda getMotivo() {
			return motivo;
		}
	}
