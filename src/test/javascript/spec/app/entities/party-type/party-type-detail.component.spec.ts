import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyTypeDetailComponent } from 'app/entities/party-type/party-type-detail.component';
import { PartyType } from 'app/shared/model/party-type.model';

describe('Component Tests', () => {
  describe('PartyType Management Detail Component', () => {
    let comp: PartyTypeDetailComponent;
    let fixture: ComponentFixture<PartyTypeDetailComponent>;
    const route = ({ data: of({ partyType: new PartyType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
