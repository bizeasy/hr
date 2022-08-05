import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UomTypeDetailComponent } from 'app/entities/uom-type/uom-type-detail.component';
import { UomType } from 'app/shared/model/uom-type.model';

describe('Component Tests', () => {
  describe('UomType Management Detail Component', () => {
    let comp: UomTypeDetailComponent;
    let fixture: ComponentFixture<UomTypeDetailComponent>;
    const route = ({ data: of({ uomType: new UomType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UomTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UomTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UomTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load uomType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uomType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
