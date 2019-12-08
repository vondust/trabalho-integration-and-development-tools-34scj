from marshmallow import Schema, fields


class LineSchema(Schema):

    reference_month = fields.Str(
        attribute="reference_month",
        load_from="MÊS_REFERÊNCIA",
        dump_to="referenceMonth",
        required=True
    )

    effective_month = fields.Str(
        attribute="effective_month",
        load_from="MÊS_COMPETÊNCIA",
        dump_to="effectiveMonth",
        required=True
    )

    state = fields.Str(
        attribute="UF",
        load_from="UF",
        dump_to="state",
        required=True
    )

    city_code = fields.Str(
        attribute="CÓDIGO_MUNICÍPIO_SIAFI",
        load_from="CÓDIGO_MUNICÍPIO_SIAFI",
        dump_to="cityCode",
        required=True
    )

    city_name = fields.Str(
        attribute="NOME_MUNICÍPIO",
        load_from="NOME_MUNICÍPIO",
        dump_to="cityName",
        required=True
    )

    grantee_number = fields.Str(
        attribute="NIS_FAVORECIDO",
        load_from="NIS_FAVORECIDO",
        dump_to="granteeNumber",
        required=True
    )

    grantee_name = fields.Str(
        attribute="NOME_FAVORECIDO",
        load_from="NOME_FAVORECIDO",
        dump_to="granteeName",
        required=True
    )

    installment_amount = fields.Str(
        attribute="VALOR_PARCELA",
        load_from="VALOR_PARCELA",
        dump_to="installmentAmount",
        required=True
    )

