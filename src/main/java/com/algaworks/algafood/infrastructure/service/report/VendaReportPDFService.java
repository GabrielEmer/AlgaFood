package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class VendaReportPDFService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        try {
            var jasperPrint = JasperFillManager.fillReport(
                    getJasperReport(),
                    getParameters(),
                    getDataSource(filtro, timeOffset));
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir o relatório.", e);
        }
    }

    private JRBeanCollectionDataSource getDataSource(VendaDiariaFilter filtro, String timeOffset) {
        return new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filtro, timeOffset));
    }

    private JasperReport getJasperReport() throws JRException {
        return JasperCompileManager.compileReport("src/main/resources/reports/vendas-diarias.jrxml");
    }

    private HashMap<String, Object> getParameters() {
        var parameters = new HashMap<String, Object>();
        parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
        return parameters;
    }
}
